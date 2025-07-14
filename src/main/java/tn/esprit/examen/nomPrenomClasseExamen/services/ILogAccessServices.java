package tn.esprit.examen.nomPrenomClasseExamen.services;

import tn.esprit.examen.nomPrenomClasseExamen.entities.LogAccess;

import java.util.List;

public interface ILogAccessServices {

    public List<LogAccess> GetAll();
    public LogAccess GetById(Long Id);
    public LogAccess Add (LogAccess logAccess);
    public LogAccess Modify(LogAccess logAccess);
    public void Delete (Long id);

}
