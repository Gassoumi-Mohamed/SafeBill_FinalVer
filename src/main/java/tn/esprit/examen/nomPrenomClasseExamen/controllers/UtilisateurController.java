package tn.esprit.examen.nomPrenomClasseExamen.controllers;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.dto.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.examen.nomPrenomClasseExamen.dto.RegisterRequest;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Role;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Utilisateur;
import tn.esprit.examen.nomPrenomClasseExamen.services.UtilisateurServices;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;


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

    @GetMapping("/GetUserById/{id}")
    public Utilisateur GetUserById(@PathVariable Long id){
        return utilisateurServices.GetById(id);
    }

    @GetMapping("/GetClients")
    public List<UtilisateurDTO> GetClients(){
        return utilisateurServices.GetByRole();
    }

    @PostMapping("AddAdmin")
    public Utilisateur AjouterAdmin(Utilisateur utilisateur){
        return utilisateurServices.AddAdmin(utilisateur);
    }



}
