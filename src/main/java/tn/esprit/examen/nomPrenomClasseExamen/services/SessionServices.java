package tn.esprit.examen.nomPrenomClasseExamen.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Session;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.SessionRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SessionServices implements ISessionServices{
    @Autowired
    SessionRepository sessionRepository;
    @Override
    public List<Session> GetAll() {
        return sessionRepository.findAll();
    }

    @Override
    public Session GetById(Long Id) {
        return sessionRepository.findById(Id).get();
    }

    @Override
    public Session Add(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public Session Modify(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public void Delete(Long id) {
        sessionRepository.deleteById(id);

    }
}
