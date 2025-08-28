package tn.esprit.examen.nomPrenomClasseExamen.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private LocalDateTime dateInscription;
    private String telephone;
    @Enumerated(EnumType.STRING)
    private Role role;



    @OneToMany(mappedBy="utilisateur")
    private Set<Session> sessions;


    @OneToMany(mappedBy="user", cascade=CascadeType.ALL)
    private Set<Facture> factures;

}
