package tn.esprit.examen.nomPrenomClasseExamen.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.examen.nomPrenomClasseExamen.entities.SignatureNum;
import tn.esprit.examen.nomPrenomClasseExamen.services.SignatureServices;

@RestController
@RequestMapping("/SignatureController")
@AllArgsConstructor
public class SignatureController {
    @Autowired
    private final SignatureServices signatureServices;
//
//    @PostMapping("/Signer/{factureId}")
//    public ResponseEntity<SignatureNum> signerFacture(@PathVariable Long factureId) {
//        try {
//            SignatureNum signature = signatureServices.signerFacture(factureId);
//            return ResponseEntity.ok(signature);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().build();
//        }
//    }

}
