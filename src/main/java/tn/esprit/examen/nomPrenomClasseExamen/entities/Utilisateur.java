package tn.esprit.examen.nomPrenomClasseExamen.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime dateInscription;
    private String telephone;


    @OneToMany(mappedBy="user")
    private Set<Notification> notifications;

    @OneToMany(mappedBy="user")
    private Set<Verification> verifications;

    @OneToMany(mappedBy="utilisateur")
    private Set<Session> sessions;

    @OneToMany(mappedBy="user")
    private Set<LogAccess> logAccesses;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL)
    private Set<Facture> factures;

}
