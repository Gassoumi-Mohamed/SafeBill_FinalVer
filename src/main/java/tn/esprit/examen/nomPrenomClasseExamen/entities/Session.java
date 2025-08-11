package tn.esprit.examen.nomPrenomClasseExamen.entities;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSession;
    private String token;
    private String ip;
    private String userAgent;
    private LocalDateTime dateConnexion;
    private LocalDateTime dateExpiration;
    private LocalDateTime derniereActivite;
    @Enumerated(EnumType.STRING)
    private Statut statut;


    @ManyToOne
    @JoinColumn(name="user_id")
    private Utilisateur utilisateur;


}
