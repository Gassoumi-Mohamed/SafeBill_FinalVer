package tn.esprit.examen.nomPrenomClasseExamen.services;

import tn.esprit.examen.nomPrenomClasseExamen.entities.TentativeFraude;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Utilisateur;

import java.util.List;

public interface IUtilisateurServices {

    public List<Utilisateur> GetAll();
    public Utilisateur GetById(Long Id);
    public Utilisateur Add (Utilisateur utilisateur);
    public Utilisateur Modify(Utilisateur utilisateur);
    public void Delete (Long id);
}
