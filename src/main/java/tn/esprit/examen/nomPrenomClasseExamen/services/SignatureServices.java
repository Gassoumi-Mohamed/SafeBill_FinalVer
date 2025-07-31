package tn.esprit.examen.nomPrenomClasseExamen.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Facture;
import tn.esprit.examen.nomPrenomClasseExamen.entities.SignatureNum;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.SignatureRepository;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Signature;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;



@Service
@AllArgsConstructor
@Slf4j
public class SignatureServices implements ISignatureServices{
    @Autowired
    SignatureRepository signatureRepository;



    public String buildDataToHash(Facture facture) {
        String numero = facture.getNumero();
        float montant = facture.getMontant();
        LocalDateTime dateEmission = facture.getDateEmission();
        // Bien formatter la date (optionnel)
        String formattedDate = dateEmission.toString(); // ou avec DateTimeFormatter

        String dataToHash = numero + "|" + montant + "|" + formattedDate;
        return dataToHash;
    }

    public String generateSignature(String dataToHash) throws Exception {
        // 1. Obtenir la clé privée
        PrivateKey privateKey = loadPrivateKey(); // méthode définie ci-dessous

        // 2. Créer l'objet Signature
        java.security.Signature rsa = Signature.getInstance("SHA256withRSA");
        rsa.initSign(privateKey);
        rsa.update(dataToHash.getBytes(StandardCharsets.UTF_8));

        // 3. Signer et encoder en base64
        byte[] signatureBytes = rsa.sign();
        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    // Méthode fictive pour charger la clé privée (à adapter)
    private PrivateKey loadPrivateKey() throws Exception {
        // Si tu utilises un fichier .pem ou un keystore, il faut adapter ici.
        // Ici, je génère une clé RSA à la volée (POUR TEST UNIQUEMENT)
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        return keyPair.getPrivate();
    }














    @Override
    public List<SignatureNum> GetAll() {
        return signatureRepository.findAll();
    }

    @Override
    public SignatureNum GetById(Long Id) {
        return signatureRepository.findById(Id).get();
    }

    @Override
    public SignatureNum Add(SignatureNum signature) {
        return signatureRepository.save(signature);
    }

    @Override
    public SignatureNum Modify(SignatureNum signature) {
        return signatureRepository.save(signature);
    }

    @Override
    public void Delete(Long id) {
        signatureRepository.deleteById(id);

    }
}
