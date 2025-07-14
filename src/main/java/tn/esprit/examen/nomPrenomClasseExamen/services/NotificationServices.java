package tn.esprit.examen.nomPrenomClasseExamen.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Notification;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.NotificationRepository;

import java.util.List;
@Slf4j
@Service
@AllArgsConstructor
public class NotificationServices implements INotificationServices{
    @Autowired
    NotificationRepository notificationRepository;
    @Override
    public List<Notification> GetAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification GetById(Long Id) {
        return notificationRepository.findById(Id).get();
    }

    @Override
    public Notification Add(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification Modify(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public void Delete(Long id) {
        notificationRepository.deleteById(id);

    }
}
