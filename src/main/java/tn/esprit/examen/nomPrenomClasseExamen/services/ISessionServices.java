package tn.esprit.examen.nomPrenomClasseExamen.services;

import tn.esprit.examen.nomPrenomClasseExamen.entities.Notification;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Session;

import java.util.List;

public interface ISessionServices {
    public List<Session> GetAll();
    public Session GetById(Long Id);
    public Session Add (Session session);
    public Session Modify(Session session);
    public void Delete (Long id);
}
