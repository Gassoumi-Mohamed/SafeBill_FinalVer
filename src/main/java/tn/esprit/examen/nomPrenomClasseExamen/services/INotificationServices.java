package tn.esprit.examen.nomPrenomClasseExamen.services;

import tn.esprit.examen.nomPrenomClasseExamen.entities.LogAccess;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Notification;

import java.util.List;

public interface INotificationServices {

    public List<Notification> GetAll();
    public Notification GetById(Long Id);
    public Notification Add (Notification notification);
    public Notification Modify(Notification notification);
    public void Delete (Long id);
}
