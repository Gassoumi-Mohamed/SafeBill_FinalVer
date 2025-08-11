//package tn.esprit.examen.nomPrenomClasseExamen.tools;
//
//import org.bouncycastle.openssl.PEMParser;
//import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
//import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.openssl.PEMException;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.security.PrivateKey;
//import java.security.Security;
//import java.security.spec.InvalidKeySpecException;
//
//public class KeyLoaderUtil {
//
//    static {
//        // Enregistre le fournisseur Bouncy Castle une seule fois
//        Security.addProvider(new BouncyCastleProvider());
//    }
//
//    /**
//     * Charge une clé privée à partir d'un fichier PEM.
//     * @param filepath Le chemin d'accès au fichier PEM de la clé privée.
//     * @return La clé privée.
//     * @throws IOException Si une erreur de lecture du fichier se produit.
//     * @throws PEMException Si une erreur de parsing PEM se produit.
//     * @throws InvalidKeySpecException Si la spécification de la clé est invalide.
//     */
//    public static PrivateKey loadPrivateKey(String filepath) throws IOException, PEMException, InvalidKeySpecException {
//        try (PEMParser pemParser = new PEMParser(new FileReader(filepath))) {
//            Object object = pemParser.readObject();
//
//            if (object instanceof PrivateKeyInfo) {
//                JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
//                PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) object;
//                return converter.getPrivateKey(privateKeyInfo);
//            } else {
//                throw new IllegalArgumentException("Le fichier PEM ne contient pas une clé privée valide.");
//            }
//        }
//    }
//}


package tn.esprit.examen.nomPrenomClasseExamen.tools;

import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.core.io.ClassPathResource;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PrivateKey;
import java.security.Security;

public class KeyLoaderUtil {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static PrivateKey loadPrivateKey(String filepath) throws Exception {
        Path path = new ClassPathResource(filepath).getFile().toPath();
        try (PEMParser pemParser = new PEMParser(new FileReader(path.toFile()))) {
            Object object = pemParser.readObject();
            if (object instanceof PrivateKeyInfo) {
                JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
                return converter.getPrivateKey((PrivateKeyInfo) object);
            } else {
                throw new IllegalArgumentException("Le fichier ne contient pas une clé privée valide.");
            }
        }
    }

    public static String loadCertificateBase64(String filepath) throws IOException {
        Path path = new ClassPathResource(filepath).getFile().toPath();
        byte[] certBytes = Files.readAllBytes(path);
        String certContent = new String(certBytes)
                .replace("-----BEGIN CERTIFICATE-----", "")
                .replace("-----END CERTIFICATE-----", "")
                .replaceAll("\\s+", "");
        return certContent;
    }
}
