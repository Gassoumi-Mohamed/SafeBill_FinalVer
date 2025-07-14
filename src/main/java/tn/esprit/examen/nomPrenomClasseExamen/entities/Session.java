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
public class Session {
    @Id
    @GeneratedValue
    private Long idSession;
    private String token;
    private String ip;
    private String userAgent;
    private LocalDateTime dateConnexion;
    private LocalDateTime dateExpiration;
    private LocalDateTime derniereActivite;
    private Statut statut;


    @ManyToOne
    private Utilisateur user;
}
