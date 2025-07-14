package tn.esprit.examen.nomPrenomClasseExamen.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Facture;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.FactureRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class FactureServices implements IFactureServices{

    @Autowired
    FactureRepository factureRepository;
    @Override
    public Facture GetFactureById(Long IdF) {
        return factureRepository.findById(IdF).get();
    }

    @Override
    public List<Facture> GetAllFacture() {
        return factureRepository.findAll();
    }

    @Override
    public Facture AddFacture(Facture facture) {
        return factureRepository.save(facture);
    }

    @Override
    public Facture ModifyFacture(Facture facture) {
        return factureRepository.save(facture);
    }

    @Override
    public void DeleteFacture(Long IdF) {
        factureRepository.deleteById(IdF);
    }
}
