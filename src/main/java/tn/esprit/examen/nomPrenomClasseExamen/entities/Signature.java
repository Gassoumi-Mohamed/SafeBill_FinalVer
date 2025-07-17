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
public class Signature {
    @Id
    @GeneratedValue
    private Long idSignature;
    private String empreinte;
    private String signatureNumerique;
    private String algorithmeHash;
    private String algorithmeSignature;
    private LocalDateTime dateSignature;
    private String clePublicHash;
    private String clePriveeHash;
    @Enumerated(EnumType.STRING)
    private StatutValidation statutValidation;
    private LocalDateTime dateVerification;


    @OneToOne(mappedBy="signature")
    private Facture facture;
}
