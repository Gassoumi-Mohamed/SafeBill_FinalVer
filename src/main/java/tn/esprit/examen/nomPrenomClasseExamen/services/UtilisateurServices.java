package tn.esprit.examen.nomPrenomClasseExamen.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Utilisateur;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.UtilisateurRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UtilisateurServices implements IUtilisateurServices{
    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Override
    public List<Utilisateur> GetAll() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Utilisateur GetById(Long Id) {
        return utilisateurRepository.findById(Id).get();
    }

    @Override
    public Utilisateur Add(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Utilisateur Modify(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public void Delete(Long id) {
        utilisateurRepository.deleteById(id);

    }
}
