package tn.esprit.examen.nomPrenomClasseExamen.services;

import tn.esprit.examen.nomPrenomClasseExamen.entities.Session;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Signature;

import java.util.List;

public interface ISignatureServices {
    public List<Signature> GetAll();
    public Signature GetById(Long Id);
    public Signature Add (Signature signature);
    public Signature Modify(Signature signature);
    public void Delete (Long id);
}
