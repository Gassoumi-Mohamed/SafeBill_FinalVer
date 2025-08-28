package tn.esprit.examen.nomPrenomClasseExamen.dto;

import tn.esprit.examen.nomPrenomClasseExamen.entities.Role;

public record AuthResponse(String token,Role role, Long id) {

}
