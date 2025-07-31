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
public class Facture {
    @Id
    @GeneratedValue
    private Long idFacture;
    private String numero;
    private float montant;
    private LocalDateTime dateEmission;
    @Enumerated(EnumType.STRING)
    private StatutFacture statutFacture;


    @OneToMany(mappedBy="facture")
    private Set<Verification> verifications;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "tentative_id")
    private TentativeFraude tentativeFraude;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "signature_id")
    private SignatureNum signature;

    @ManyToOne
    private Utilisateur user;
}
