package tn.esprit.examen.nomPrenomClasseExamen.controllers;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Statut;
import tn.esprit.examen.nomPrenomClasseExamen.services.*;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Utilisateur;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.UtilisateurRepository;
import tn.esprit.examen.nomPrenomClasseExamen.services.*;
import tn.esprit.examen.nomPrenomClasseExamen.dto.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final SessionServices sessionServices;

    private final AuthenticationManager authManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UtilisateurRepository userRepo;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request, HttpServletRequest httpRequest) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        UserDetails user = userDetailsService.loadUserByUsername(request.email());
        String token = jwtService.generateToken(user.getUsername());
        String ip = httpRequest.getRemoteAddr();
        String userAgent = httpRequest.getHeader("User-Agent");
        Utilisateur utilisateur = userRepo.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));
        // 4. créer la session en base String
        sessionServices.createSession(utilisateur, token, ip, userAgent, LocalDateTime.now().plusHours(1), Statut.ACTIVE); // expiration 1h

        return new AuthResponse(token, utilisateur.getRole(), utilisateur.getId());
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Aucun token fourni");
        }

        String token = authHeader.substring(7); // Retirer "Bearer "
        boolean result = sessionServices.revokeToken(token);

        if (result) {
            return ResponseEntity.ok("Déconnexion réussie");
        } else {
            return ResponseEntity.status(404).body("Session introuvable ou déjà révoquée");
        }
    }
}
