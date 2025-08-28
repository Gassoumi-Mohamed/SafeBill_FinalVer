package tn.esprit.examen.nomPrenomClasseExamen.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TentativeFraude {
    @Id
    @GeneratedValue
    private Long idT;
    private String description;
    private LocalDateTime dateDetection;



}
