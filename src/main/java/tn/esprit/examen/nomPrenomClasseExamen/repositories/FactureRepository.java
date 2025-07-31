package tn.esprit.examen.nomPrenomClasseExamen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Facture;

public interface FactureRepository extends JpaRepository<Facture, Long> {
    public Facture findByidFacture (Long idFacture);

}
