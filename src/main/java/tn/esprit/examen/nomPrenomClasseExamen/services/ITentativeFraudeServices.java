package tn.esprit.examen.nomPrenomClasseExamen.services;

import tn.esprit.examen.nomPrenomClasseExamen.entities.TentativeFraude;

import java.util.List;

public interface ITentativeFraudeServices {

    public List<TentativeFraude> GetAll();
    public TentativeFraude GetById(Long Id);
    public TentativeFraude Add (TentativeFraude tentativeFraude);
    public TentativeFraude Modify(TentativeFraude tentativeFraude);
    public void Delete (Long id);
}
