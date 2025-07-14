package tn.esprit.examen.nomPrenomClasseExamen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.nomPrenomClasseExamen.entities.TentativeFraude;

public interface TentativeFraudeRepository extends JpaRepository<TentativeFraude, Long> {
}
