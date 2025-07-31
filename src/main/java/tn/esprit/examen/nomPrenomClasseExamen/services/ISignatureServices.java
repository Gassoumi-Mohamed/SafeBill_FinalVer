package tn.esprit.examen.nomPrenomClasseExamen.services;

import tn.esprit.examen.nomPrenomClasseExamen.entities.SignatureNum;

import java.util.List;

public interface ISignatureServices {
    public List<SignatureNum> GetAll();
    public SignatureNum GetById(Long Id);
    public SignatureNum Add (SignatureNum signature);
    public SignatureNum Modify(SignatureNum signature);
    public void Delete (Long id);
}
