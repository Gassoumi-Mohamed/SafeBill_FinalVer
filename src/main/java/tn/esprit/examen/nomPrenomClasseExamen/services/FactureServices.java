package tn.esprit.examen.nomPrenomClasseExamen.services;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Facture;
import tn.esprit.examen.nomPrenomClasseExamen.entities.SignatureNum;
import tn.esprit.examen.nomPrenomClasseExamen.entities.StatutFacture;
import tn.esprit.examen.nomPrenomClasseExamen.entities.StatutValidation;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.FactureRepository;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.SignatureRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class FactureServices implements IFactureServices{

    private final FactureRepository factureRepository;
    private final SignatureRepository signatureNumRepository;

    public Facture CreateFacture (Facture facture){
        return factureRepository.save(facture);
    }

    // Méthode pour charger la clé privée depuis un fichier PEM
    private PrivateKey loadPrivateKeyFromPemFile() throws Exception {
        String privateKeyContent = new String(Files.readAllBytes(Paths.get("src/main/resources/keys/private_key_pkcs8.pem")));
        // Nettoyage plus robuste
        privateKeyContent = privateKeyContent
                .replaceAll("-----BEGIN.*?-----", "")
                .replaceAll("-----END.*?-----", "")
                .replaceAll("\\s", "");  // Supprime tous les espaces et sauts de ligne

        try {
            byte[] decoded = Base64.getDecoder().decode(privateKeyContent);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(keySpec);
        } catch (IllegalArgumentException e) {
            throw new Exception("Erreur de décodage Base64 - Contenu clé invalide: " + e.getMessage());
        }
    }
    private void validateBase64(String content) throws Exception {
        if (!content.matches("^[a-zA-Z0-9+/]*={0,2}$")) {
            throw new Exception("Le contenu n'est pas un Base64 valide");
        }

    }
    // Méthode pour charger la clé publique depuis un fichier PEM
    private PublicKey loadPublicKeyFromPemFile() throws Exception {
        String publicKeyContent = new String(Files.readAllBytes(Paths.get("src/main/resources/keys/public_key.pem")));
        publicKeyContent = publicKeyContent
                .replaceAll("-----BEGIN.*?-----", "")
                .replaceAll("-----END.*?-----", "")
                .replaceAll("\\s", "");

        validateBase64(publicKeyContent);

        try {
            byte[] decoded = Base64.getDecoder().decode(publicKeyContent);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(keySpec);
        } catch (IllegalArgumentException e) {
            throw new Exception("Erreur de décodage clé publique: " + e.getMessage());
        }
    }
    public void verifCle(){
        try {
            PublicKey pub = loadPublicKeyFromPemFile();
            PrivateKey priv = loadPrivateKeyFromPemFile();
            System.out.println("Clés chargées avec succès");
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
        }

    }


    // Méthode principale pour créer et sauvegarder une facture avec sa signature
    public Facture createFactureWithSignature(Facture facture) throws Exception {
        // Le contenu de la facture à signer
        String montantStr = String.format("%.2f", facture.getMontant());
        String documentASigner = facture.getNumero() + montantStr + facture.getDateEmission().toString();

        // 1. Charger les clés
        PrivateKey privateKey = loadPrivateKeyFromPemFile();
        PublicKey publicKey = loadPublicKeyFromPemFile();

        // 2. Hacher le contenu de la facture
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] documentBytes = documentASigner.getBytes("UTF-8");
        byte[] empreinteBytes = md.digest(documentBytes);
        String empreinte = Base64.getEncoder().encodeToString(empreinteBytes);

        // 3. Créer la signature numérique
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(documentBytes);
        byte[] signatureBytes = signature.sign();
        String signatureNumerique = Base64.getEncoder().encodeToString(signatureBytes);

        // 4. Hacher les clés pour la sauvegarde
        MessageDigest mdKeys = MessageDigest.getInstance("SHA-256");
        String clePublicHash = Base64.getEncoder().encodeToString(mdKeys.digest(publicKey.getEncoded()));
        // Note : On ne stocke pas le hachage de la clé privée pour la sécurité
        // String clePriveeHash = Base64.getEncoder().encodeToString(mdKeys.digest(privateKey.getEncoded()));

        // 5. Créer l'entité SignatureNum et la lier à la facture

        SignatureNum signatureEntity = SignatureNum.builder()
                .empreinte(empreinte)
                .signatureNumerique(signatureNumerique)
                .dateSignature(LocalDateTime.now())
                .statutValidation(StatutValidation.NON_VERIFIEE)
                .build();
        SignatureNum sig = signatureNumRepository.save(signatureEntity);

        // Associer la signature à la facture
        facture.setSignature(sig);
        System.out.println("Signature numérique générée : " + signatureNumerique);
        System.out.println("Longueur : " + signatureNumerique.length());


        // 6. Sauvegarder la facture, ce qui va aussi sauvegarder la signature grâce au cascade
        return factureRepository.save(facture);
    }

    //
    // Nouvelle méthode pour générer le QR code
    public byte[] generateFactureQrCode(Long factureId) throws Exception {
        // 1. Récupérer la facture existante avec sa signature
        Facture facture = factureRepository.findById(factureId)
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));

        // 2. Construire l'objet QR avec toutes les données nécessaires
        String montantStr = String.format("%.2f", facture.getMontant());
        QrData qrData = new QrData(
                facture.getNumero(),
                facture.getDateEmission().toString(),
                montantStr,
                facture.getSignature().getSignatureNumerique(), // Utilisation de la signature existante
                LocalDateTime.now(),
                facture.getStatutFacture() == StatutFacture.PAYEE,
                generateAntiReplayToken()
        );

        // 3. Convertir en JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        String jsonData = mapper.writeValueAsString(qrData);

        // 4. Générer l'image QR code
        return generateQrCodeImage(jsonData);
    }

    // Méthodes utilitaires pour le QR code
    private String generateAntiReplayToken() {
        return UUID.randomUUID().toString();
    }

    private byte[] generateQrCodeImage(String data) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 2);

        BitMatrix bitMatrix = new QRCodeWriter().encode(
                data,
                BarcodeFormat.QR_CODE,
                400, 400,
                hints
        );

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }

    // Classe interne pour la structure des données QR
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class QrData {
        private String numeroFacture;
        private String dateEmission;
        private String montant;
        private String signature;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime timestampGeneration;
        private boolean estPayee;
        private String antiReplayToken;
    }

    // Méthode de vérification adaptée à votre structure
    public VerificationResponse verifyQrCode(String qrCodeData) throws Exception {
        // 1. Décodage du QR code
        QrData qrData = decodeQrData(qrCodeData);

        // 2. Vérification des champs obligatoires
        if (qrData.getNumeroFacture() == null || qrData.getSignature() == null) {
            return VerificationResponse.invalid("Données manquantes dans le QR code");
        }

        // 3. Vérification de la signature
        String message = qrData.getNumeroFacture() + qrData.getMontant() + qrData.getDateEmission();
        boolean isSignatureValid = verifySignature(
                message,
                qrData.getSignature(),
                loadPublicKeyFromPemFile()
        );

        if (!isSignatureValid) {
            return VerificationResponse.invalid("Signature invalide");
        }

        // 4. Vérification du statut en base
        Facture facture = factureRepository.findFactureBynumero(qrData.getNumeroFacture())
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));

        if (facture.getSignature().getStatutValidation() == StatutValidation.VALIDE) {
            return VerificationResponse.invalid("Facture déjà payée");
        }

        return VerificationResponse.valid(facture);
    }

    private boolean verifySignature(String message, String signatureBase64, PublicKey publicKey) throws Exception {
        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(publicKey);
        verifier.update(message.getBytes(StandardCharsets.UTF_8));
        return verifier.verify(Base64.getDecoder().decode(signatureBase64));
    }

    private QrData decodeQrData(String qrCodeData) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        return mapper.readValue(qrCodeData, QrData.class);
    }

    // Classe de réponse pour la vérification
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VerificationResponse {
        private boolean valide;
        private String message;
        private Facture facture;

        public static VerificationResponse valid(Facture facture) {
            return new VerificationResponse(true, "Vérification réussie", facture);
        }

        public static VerificationResponse invalid(String reason) {
            return new VerificationResponse(false, reason, null);
        }
    }


}
