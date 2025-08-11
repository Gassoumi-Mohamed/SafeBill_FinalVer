package tn.esprit.examen.nomPrenomClasseExamen;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.security.Security;
//import tn.esprit.examen.nomPrenomClasseExamen.tools.KeyGeneratorUtil;

@EnableAspectJAutoProxy
@EnableScheduling
@SpringBootApplication
public class nomPrenomClasseExamenApplication {

    public static void main(String[] args) {
        SpringApplication.run(nomPrenomClasseExamenApplication.class, args);
        Security.addProvider(new BouncyCastleProvider());
//        KeyGeneratorUtil.generateAndSaveCertificate();
//        System.out.println("Clé privée, publique et certificat générés !");
        
    }

}
