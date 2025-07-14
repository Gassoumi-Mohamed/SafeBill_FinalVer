package tn.esprit.examen.nomPrenomClasseExamen.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.LogAccess;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.LogAccessRepository;

import java.util.List;
@Service
@AllArgsConstructor
@Slf4j
public class LogAccessServices implements ILogAccessServices{
    @Autowired
    LogAccessRepository logAccessRepository;
    @Override
    public List<LogAccess> GetAll() {
        return logAccessRepository.findAll();
    }

    @Override
    public LogAccess GetById(Long Id) {
        return logAccessRepository.findById(Id).get();
    }

    @Override
    public LogAccess Add(LogAccess logAccess) {
        return logAccessRepository.save(logAccess);
    }

    @Override
    public LogAccess Modify(LogAccess logAccess) {
        return logAccessRepository.save(logAccess);
    }

    @Override
    public void Delete(Long id) {
        logAccessRepository.deleteById(id);
    }
}
