package tn.esprit.examen.nomPrenomClasseExamen.services;

import tn.esprit.examen.nomPrenomClasseExamen.entities.Facture;

import java.util.List;

public interface IFactureServices {
    public Facture GetFactureById(Long IdF);
    public List<Facture>GetAllFacture();
    public Facture AddFacture(Facture facture);
    public Facture ModifyFacture(Facture facture);
    public void DeleteFacture(Long IdF);
}
