//package tn.esprit.examen.nomPrenomClasseExamen.tools;
//
//import org.bouncycastle.cert.X509CertificateHolder;
//import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
//import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.operator.ContentSigner;
//import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
//import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
//
//import java.io.*;
//import java.math.BigInteger;
//import java.security.*;
//import java.security.cert.CertificateFactory;
//import java.security.cert.X509Certificate;
//import java.util.Base64;
//import java.util.Date;
//
//import javax.security.auth.x500.X500Principal;
//
//public class KeyGeneratorUtil {
//
//    static {
//        Security.addProvider(new BouncyCastleProvider());
//    }
//
//    public static void generateAndSaveCertificate() throws Exception {
//        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
//        keyGen.initialize(2048);
//        KeyPair pair = keyGen.generateKeyPair();
//
//        // Certificat valide 1 an
//        long now = System.currentTimeMillis();
//        Date notBefore = new Date(now);
//        Date notAfter = new Date(now + 365L * 24 * 60 * 60 * 1000); // 1 an
//
//        X500Principal subject = new X500Principal("CN=Ooredoo, O=SafeBill, C=TN");
//
//        JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
//                subject,
//                new BigInteger(Long.toString(now)),
//                notBefore,
//                notAfter,
//                subject,
//                pair.getPublic()
//        );
//
//        ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA").build(pair.getPrivate());
//        X509CertificateHolder holder = certBuilder.build(signer);
//
//        X509Certificate cert = new JcaX509CertificateConverter()
//                .setProvider("BC")
//                .getCertificate(holder);
//        File directory = new File("keys");
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
//
//        // Sauvegarder dans des fichiers
//        savePEM("keys/private_key.pem", pair.getPrivate());
//        savePEM("keys/public_key.pem", pair.getPublic());
//        saveCert("keys/certificate.crt", cert);
//    }
//
//    private static void savePEM(String filename, Object key) throws IOException {
//        try (JcaPEMWriter writer = new JcaPEMWriter(new FileWriter(filename))) {
//            writer.writeObject(key);
//        }
//    }
//
//    private static void saveCert(String path, X509Certificate cert) throws Exception {
//        try (FileWriter fw = new FileWriter(path)) {
//            fw.write("-----BEGIN CERTIFICATE-----\n");
//            fw.write(Base64.getEncoder().encodeToString(cert.getEncoded()));
//            fw.write("\n-----END CERTIFICATE-----");
//        }
//    }
//
//    // Bonus : pour attacher à SignatureNum plus tard
//    public static String getCertAsBase64() throws Exception {
//        try (FileInputStream fis = new FileInputStream("keys/certificate.crt")) {
//            return Base64.getEncoder().encodeToString(fis.readAllBytes());
//        }
//    }
//}
