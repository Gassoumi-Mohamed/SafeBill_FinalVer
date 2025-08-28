//package tn.esprit.examen.nomPrenomClasseExamen.services;
//
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
//import org.bouncycastle.openssl.PEMParser;
//import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import tn.esprit.examen.nomPrenomClasseExamen.entities.Facture;
//import tn.esprit.examen.nomPrenomClasseExamen.entities.SignatureNum;
//import tn.esprit.examen.nomPrenomClasseExamen.repositories.FactureRepository;
//import tn.esprit.examen.nomPrenomClasseExamen.repositories.SignatureRepository;
//
//import java.io.FileReader;
//import java.nio.charset.StandardCharsets;
//import java.security.PrivateKey;
//import java.security.Signature;
//import java.time.LocalDateTime;
//import java.util.Base64;
//
//
//@Service
//    @AllArgsConstructor
//    @Slf4j
//    public class SignatureServices implements ISignatureServices {
//
//        @Autowired
//        private SignatureRepository signatureRepository;
//
//        @Autowired
//        private FactureRepository factureRepository;
//
//        // Construit la donnée à hasher/signature depuis la facture
//        public String buildDataToHash(Facture facture) {
//            String numero = facture.getNumero();
//            float montant = facture.getMontant();
//            LocalDateTime dateEmission = facture.getDateEmission();
//            String formattedDate = dateEmission.toString();
//            return numero + "|" + montant + "|" + formattedDate;
//        }
//
//        // Charge la clé privée depuis un fichier PEM
//        private PrivateKey loadPrivateKey() throws Exception {
//            try (PEMParser pemParser = new PEMParser(new FileReader("keys/private_key.pem"))) {
//                Object object = pemParser.readObject();
//                JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
//                if (object instanceof PrivateKeyInfo) {
//                    return converter.getPrivateKey((PrivateKeyInfo) object);
//                } else {
//                    throw new IllegalArgumentException("Le fichier ne contient pas une clé privée valide.");
//                }
//            }
//        }
//
//        // Génère la signature Base64 pour une donnée avec la clé privée
//        public String generateSignature(String dataToHash, PrivateKey privateKey) throws Exception {
//            Signature signer = Signature.getInstance("SHA256withRSA");
//            signer.initSign(privateKey);
//            signer.update(dataToHash.getBytes(StandardCharsets.UTF_8));
//            byte[] signatureBytes = signer.sign();
//            return Base64.getEncoder().encodeToString(signatureBytes);
//        }
//
//        // Charge le certificat (en Base64) depuis le fichier
//        public String getCertificateBase64() throws Exception {
//            byte[] certBytes = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("keys/certificate.crt"));
//            // On enlève les lignes BEGIN/END, on encode le contenu en base64
//            String certContent = new String(certBytes)
//                    .replace("-----BEGIN CERTIFICATE-----", "")
//                    .replace("-----END CERTIFICATE-----", "")
//                    .replaceAll("\\s+", "");
//            return certContent;
//        }
//
//        // Méthode principale : signe une facture
//        public SignatureNum signerFacture(Long factureId) throws Exception {
//            Facture facture = factureRepository.findById(factureId)
//                    .orElseThrow(() -> new RuntimeException("Facture non trouvée"));
//
//            // Construire la donnée à signer
//            String dataToHash = buildDataToHash(facture);
//
//            // Charger clé privée
//            PrivateKey privateKey = loadPrivateKey();
//
//            // Générer signature
//            String signatureBase64 = generateSignature(dataToHash, privateKey);
//
//            // Charger certificat
//            String certBase64 = getCertificateBase64();
//
//            // Créer signature numérique
//            SignatureNum signatureNum = new SignatureNum();
//            signatureNum.setEmpreinte(dataToHash);
//            signatureNum.setSignatureNumerique(signatureBase64);
//            signatureNum.setAlgorithmeHash("SHA-256");
//            signatureNum.setAlgorithmeSignature("RSA");
//            signatureNum.setDateSignature(LocalDateTime.now());
//            signatureNum.setClePublicHash(certBase64);
//            signatureNum.setStatutValidation(tn.esprit.examen.nomPrenomClasseExamen.entities.StatutValidation.NON_VERIFIEE);
//
//            // Sauvegarder signature
//            signatureNum = signatureRepository.save(signatureNum);
//
//            // Associer à la facture et sauvegarder
//            facture.setSignature(signatureNum);
//            factureRepository.save(facture);
//
//            return signatureNum;
//        }
//
//
//    }


package tn.esprit.examen.nomPrenomClasseExamen.services;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Facture;
import tn.esprit.examen.nomPrenomClasseExamen.entities.SignatureNum;
import tn.esprit.examen.nomPrenomClasseExamen.entities.StatutValidation;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.FactureRepository;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.SignatureRepository;
//import tn.esprit.examen.nomPrenomClasseExamen.tools.KeyLoaderUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@Slf4j
public class SignatureServices implements ISignatureServices {


    @Autowired
    SignatureRepository signatureRepository;

    //    private final String certBase64;
//
//    static {
//        Security.addProvider(new BouncyCastleProvider());
//    }
//
//    public SignatureServices(SignatureRepository signatureRepository, FactureRepository factureRepository) throws Exception {
//        this.signatureRepository = signatureRepository;
//        this.factureRepository = factureRepository;
//
//        try {
//            this.privateKey = KeyLoaderUtil.loadPrivateKey("keys/private_key.pem");
//            this.certBase64 = KeyLoaderUtil.loadCertificateBase64("keys/certificate.crt");
//        } catch (IOException | InvalidKeySpecException e) {
//            log.error("Erreur critique: Échec du chargement de la clé privée ou du certificat.", e);
//            throw new RuntimeException("Échec du démarrage du service de signature.", e);
//        }
//    }
//
//    private String buildDataToSign(Facture facture) {
//        String numero = facture.getNumero();
//        float montant = facture.getMontant();
//        LocalDateTime dateEmission = facture.getDateEmission();
//        String formattedDate = dateEmission.toString();
//        return numero + "|" + montant + "|" + formattedDate;
//    }
//
//    private String generateSignature(String dataToSign) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
//        Signature signer = Signature.getInstance("SHA256withRSA");
//        signer.initSign(privateKey);
//        signer.update(dataToSign.getBytes(StandardCharsets.UTF_8));
//        byte[] signatureBytes = signer.sign();
//        return Base64.getEncoder().encodeToString(signatureBytes);
//    }
//
//
//    public SignatureNum signerFacture(Long factureId) {
//        try {
//            Facture facture = factureRepository.findById(factureId)
//                    .orElseThrow(() -> new RuntimeException("Facture non trouvée"));
//
//            String dataToSign = buildDataToSign(facture);
//            String signatureBase64 = generateSignature(dataToSign);
//
//            SignatureNum signatureNum = new SignatureNum();
//            signatureNum.setEmpreinte(dataToSign);
//            signatureNum.setSignatureNumerique(signatureBase64);
//            signatureNum.setAlgorithmeHash("SHA-256");
//            signatureNum.setAlgorithmeSignature("RSA");
//            signatureNum.setDateSignature(LocalDateTime.now());
//            signatureNum.setClePublicHash(certBase64);
//            signatureNum.setStatutValidation(StatutValidation.NON_VERIFIEE);
//
//            signatureNum = signatureRepository.save(signatureNum);
//
//            facture.setSignature(signatureNum);
//            factureRepository.save(facture);
//
//            return signatureNum;
//        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
//            log.error("Échec de la signature de la facture avec l'ID {}", factureId, e);
//            throw new RuntimeException("Échec du processus de signature.", e);
//        }

}












