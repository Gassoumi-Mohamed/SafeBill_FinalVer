package tn.esprit.examen.nomPrenomClasseExamen.controllers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Facture;
import tn.esprit.examen.nomPrenomClasseExamen.services.FactureServices;
import tn.esprit.examen.nomPrenomClasseExamen.services.PdfService;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/FactureController")
@Slf4j
public class FactureController {
    @Autowired
    FactureServices factureServices;
    PdfService pdfService;


    @PostMapping("/createFactureWithSignature/{id}")
    public ResponseEntity<Facture> createFacture(@RequestBody Facture nouvelleFacture,@PathVariable Long id) {
        try {
            Facture savedFacture = factureServices.createFactureWithSignature(nouvelleFacture, id);
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
        log.info("Génération QR code pour la facture ID: {}", id);
        byte[] qrCode = factureServices.generateFactureQrCode(id);

        if (qrCode == null || qrCode.length == 0) {
            log.warn("QR code vide ou null pour la facture ID: {}", id);
            return ResponseEntity.notFound().build();
        }

        log.info("QR code généré avec succès, taille: {} bytes", qrCode.length);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrCode);
    } catch (Exception e) {
        log.error("Erreur génération QR code pour la facture ID: " + id, e);
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


    @GetMapping("/GetFactureByClientId/{id}")
    public List<Facture> GetFactureByClientId(@PathVariable Long id){
        return factureServices.GetFacturesByClientId(id);
    }

    @GetMapping("/GetByFactureId/{id}")
    public Facture GetFactureById(@PathVariable Long id){
        return factureServices.GetFactureById(id);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> getFacturePDF(@PathVariable Long id) throws Exception {
        Facture facture = factureServices.GetFactureById(id);

        // Générer le QR code dynamique
        byte[] qrCodeImage = factureServices.generateFactureQrCode(id);

        byte[] pdfBytes = pdfService.generateFacturePDF(facture, qrCodeImage);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=facture_" + facture.getNumero() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }



}
