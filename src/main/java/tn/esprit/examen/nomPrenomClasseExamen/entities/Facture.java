package tn.esprit.examen.nomPrenomClasseExamen.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFacture;
    private String numero;
    private float montant;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateEmission;
    @Enumerated(EnumType.STRING)
    private StatutFacture statutFacture;



    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "signature_id")
    private SignatureNum signature;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Utilisateur user;
}
