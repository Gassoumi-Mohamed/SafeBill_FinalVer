package tn.esprit.examen.nomPrenomClasseExamen.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Verification;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.VerificationRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class VerificationServices implements IVerificationServices{
    @Autowired
    VerificationRepository verificationRepository;

    @Override
    public List<Verification> GetAll() {
        return verificationRepository.findAll();
    }

    @Override
    public Verification GetById(Long Id) {
        return verificationRepository.findById(Id).get();
    }

    @Override
    public Verification Add(Verification verification) {
        return verificationRepository.save(verification);
    }

    @Override
    public Verification Modify(Verification verification) {
        return verificationRepository.save(verification);
    }

    @Override
    public void Delete(Long id) {
        verificationRepository.deleteById(id);
    }
}
