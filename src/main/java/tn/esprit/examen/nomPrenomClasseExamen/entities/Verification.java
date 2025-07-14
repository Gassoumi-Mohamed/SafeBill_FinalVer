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
public class Verification {
    @Id
    @GeneratedValue
    private Long idV;
    private LocalDateTime dateVerification;
    @Enumerated(EnumType.STRING)
    private Resultat resultat;



    @ManyToOne
    private Utilisateur user;

    @ManyToOne
    private Facture facture;
}
