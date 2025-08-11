package tn.esprit.examen.nomPrenomClasseExamen.controllers;
import tn.esprit.examen.nomPrenomClasseExamen.dto.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.examen.nomPrenomClasseExamen.dto.RegisterRequest;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Utilisateur;
import tn.esprit.examen.nomPrenomClasseExamen.services.UtilisateurServices;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;



@RestController
@RequestMapping("/RegisterController")
@AllArgsConstructor
public class UtilisateurController {
    @Autowired
    UtilisateurServices utilisateurServices;
    @PostMapping("/Register")
    public ResponseEntity<Utilisateur> registerUser(@Valid @RequestBody RegisterRequest request) {
        Utilisateur createdUser = utilisateurServices.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }



}
