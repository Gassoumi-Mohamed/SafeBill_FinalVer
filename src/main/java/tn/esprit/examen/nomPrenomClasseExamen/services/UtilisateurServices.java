package tn.esprit.examen.nomPrenomClasseExamen.services;
import tn.esprit.examen.nomPrenomClasseExamen.config.SecurityConfig;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.dto.RegisterRequest;
import tn.esprit.examen.nomPrenomClasseExamen.dto.UtilisateurDTO;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Role;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Utilisateur;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.UtilisateurRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UtilisateurServices implements IUtilisateurServices{
    @Autowired
    UtilisateurRepository utilisateurRepository;
    SecurityConfig securityConfig;

    public Utilisateur register(RegisterRequest request) {
        Utilisateur utilisateur = Utilisateur.builder()
                .prenom(request.getPrenom())
                .nom(request.getNom())
                .email(request.getEmail())
                .mdp(securityConfig.passwordEncoder().encode(request.getPassword())) // à chiffrer dans une vraie app
                .telephone(request.getTelephone())
                .dateInscription(LocalDateTime.now())
                .role(request.getRole())
                .dateInscription(request.getDateInscription())
                .build();

        return utilisateurRepository.save(utilisateur);
    }



    @Override
    public List<Utilisateur> GetAll() {
        return utilisateurRepository.findAll();
    }

    public List<UtilisateurDTO> GetByRole() {
        return utilisateurRepository.findAllByRole(Role.CLIENT)
                .stream()
                .map(u -> new UtilisateurDTO(
                        u.getId(),
                        u.getNom(),
                        u.getPrenom(),
                        u.getEmail(),
                        u.getRole().toString()
                ))
                .toList();
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

    public Utilisateur AddAdmin(Utilisateur utilisateur){
        if (utilisateurRepository.existsByEmail(utilisateur.getEmail())) {
            throw new RuntimeException("Email déjà utilisé !");
        }
        utilisateur.setRole(Role.ADMIN);
        return utilisateurRepository.save(utilisateur);
    }
}
