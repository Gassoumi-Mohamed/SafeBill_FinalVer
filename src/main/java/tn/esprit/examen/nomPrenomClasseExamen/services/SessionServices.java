package tn.esprit.examen.nomPrenomClasseExamen.services;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Utilisateur;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Session;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Statut;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Utilisateur;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.SessionRepository;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.UtilisateurRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class SessionServices implements ISessionServices{

    @Autowired
    SessionRepository sessionRepository;
    UtilisateurRepository utilisateurRepository;


    public Session createSession(Utilisateur utilisateur, String token,String ip,String userAgent,LocalDateTime expiration,Statut statut) {
        Session session = new Session();
        //session.setUtilisateur(utilisateur);

        session.setUtilisateur(utilisateur);
        session.setToken(token);
        session.setIp(ip);
        session.setUserAgent(userAgent);
        session.setDateConnexion(LocalDateTime.now());
        session.setDateExpiration(expiration);
        session.setDerniereActivite(LocalDateTime.now());
        session.setStatut(statut.ACTIVE);
        return sessionRepository.save(session);

    }


    public boolean revokeToken(String token) {
        Optional<Session> sessions = sessionRepository.findByToken(token);
        if (sessions.isEmpty()) {
            return false;
        }

        Session session = sessions.get();
        if (session.getStatut() != Statut.ACTIVE) {
            return false; // déjà expiré/révoqué
        }

        session.setStatut(Statut.EXPIRE); // ou REVOQUE si tu préfères
        session.setDateExpiration(LocalDateTime.now());
        sessionRepository.save(session);

        return true;
    }

}
