package tn.esprit.examen.nomPrenomClasseExamen.controllers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Facture;
import tn.esprit.examen.nomPrenomClasseExamen.services.FactureServices;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/FactureController")
@Slf4j
public class FactureController {
    @Autowired
    FactureServices factureServices;
//    @PostMapping("/Add")
//    public Facture AddFacture (@RequestBody Facture facture){
//        return factureServices.CreateFacture(facture);
//    }
    @PostMapping("/createFactureWithSignature")
    public ResponseEntity<Facture> createFacture(@RequestBody Facture nouvelleFacture) {
        try {
            Facture savedFacture = factureServices.createFactureWithSignature(nouvelleFacture);
            return ResponseEntity.ok(savedFacture);
        } catch (Exception e) {
            // Log the error for debugging purposes
            System.err.println("Error creating facture with signature: " + e.getMessage());
            // Return a bad request response with an error message
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/factures/{id}/qrcode")
    public ResponseEntity<byte[]> getQrCode(@PathVariable Long id) {
        try {
            byte[] qrCode = factureServices.generateFactureQrCode(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrCode);
        } catch (Exception e) {
            log.error("Erreur génération QR code", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyQrCode(@RequestBody String qrCodeData) {
        try {
            FactureServices.VerificationResponse response = factureServices.verifyQrCode(qrCodeData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

}
