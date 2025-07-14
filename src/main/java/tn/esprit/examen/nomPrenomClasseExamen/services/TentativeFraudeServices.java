package tn.esprit.examen.nomPrenomClasseExamen.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.TentativeFraude;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.TentativeFraudeRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TentativeFraudeServices implements ITentativeFraudeServices{
    @Autowired
    TentativeFraudeRepository tentativeFraudeRepository;

    @Override
    public List<TentativeFraude> GetAll() {
        return tentativeFraudeRepository.findAll();
    }

    @Override
    public TentativeFraude GetById(Long Id) {
        return tentativeFraudeRepository.findById(Id).get();
    }

    @Override
    public TentativeFraude Add(TentativeFraude tentativeFraude) {
        return tentativeFraudeRepository.save(tentativeFraude);
    }

    @Override
    public TentativeFraude Modify(TentativeFraude tentativeFraude) {
        return tentativeFraudeRepository.save(tentativeFraude);
    }

    @Override
    public void Delete(Long id) {
        tentativeFraudeRepository.deleteById(id);

    }
}
