package tn.esprit.examen.nomPrenomClasseExamen.services;

import tn.esprit.examen.nomPrenomClasseExamen.entities.Utilisateur;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Verification;

import java.util.List;

public interface IVerificationServices {

    public List<Verification> GetAll();
    public Verification GetById(Long Id);
    public Verification Add (Verification verification);
    public Verification Modify(Verification verification);
    public void Delete (Long id);
}
