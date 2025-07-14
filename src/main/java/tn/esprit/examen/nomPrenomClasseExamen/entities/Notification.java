package tn.esprit.examen.nomPrenomClasseExamen.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Notification {
    @Id
    @GeneratedValue
    private Long idNotification;
    private String contenu;
    private LocalDateTime dateEnvoie;
    private Boolean estLue;

    @ManyToOne
    private Utilisateur user;
}
