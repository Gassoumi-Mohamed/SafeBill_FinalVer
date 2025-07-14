package tn.esprit.examen.nomPrenomClasseExamen.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Signature;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.SignatureRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SignatureServices implements ISignatureServices{
    @Autowired
    SignatureRepository signatureRepository;

    @Override
    public List<Signature> GetAll() {
        return signatureRepository.findAll();
    }

    @Override
    public Signature GetById(Long Id) {
        return signatureRepository.findById(Id).get();
    }

    @Override
    public Signature Add(Signature signature) {
        return signatureRepository.save(signature);
    }

    @Override
    public Signature Modify(Signature signature) {
        return signatureRepository.save(signature);
    }

    @Override
    public void Delete(Long id) {
        signatureRepository.deleteById(id);

    }
}
