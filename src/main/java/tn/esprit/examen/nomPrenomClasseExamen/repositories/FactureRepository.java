package tn.esprit.examen.nomPrenomClasseExamen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Facture;

import java.util.List;
import java.util.Optional;

public interface FactureRepository extends JpaRepository<Facture, Long> {
    public Facture findByidFacture (Long idFacture);
    Optional<Facture> findFactureBynumero (String numero);
    List<Facture> findFactureByUser_Id (Long id);
    @Query("SELECT f FROM Facture f WHERE f.user.id = :clientId")
    List<Facture> findByClientId(@Param("clientId") Long clientId);

}
