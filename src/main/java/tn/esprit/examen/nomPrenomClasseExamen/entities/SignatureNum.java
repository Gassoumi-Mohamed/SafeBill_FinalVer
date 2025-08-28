package tn.esprit.examen.nomPrenomClasseExamen.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SignatureNum {
    @Id
    @GeneratedValue
    private Long idSignature;
    private String empreinte;
    @Column(length = 10000)
    private String signatureNumerique;
    private LocalDateTime dateSignature;
    @Enumerated(EnumType.STRING)
    private StatutValidation statutValidation;


    @OneToOne(mappedBy="signature")
    @JsonIgnore
    private Facture facture;
}

