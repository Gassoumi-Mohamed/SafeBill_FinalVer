package tn.esprit.examen.nomPrenomClasseExamen.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Utilisateur;
import tn.esprit.examen.nomPrenomClasseExamen.services.UtilisateurServices;

@RestController
@RequestMapping("/Utilisateur")
@AllArgsConstructor
public class UtilisateurController {
    @Autowired
    UtilisateurServices utilisateurServices;
    @PostMapping("/Register")
    public Utilisateur CreateUser(@RequestBody Utilisateur utilisateur){
        return utilisateurServices.Add(utilisateur);
    }
}
