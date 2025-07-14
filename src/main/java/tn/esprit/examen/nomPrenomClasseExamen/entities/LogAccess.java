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
public class LogAccess {
    @Id
    @GeneratedValue
    private Long idL;
    private String userAgent;
    private String ip;
    private String action;
    private LocalDateTime date;


    @ManyToOne
    private Utilisateur user;
}
