package tn.esprit.examen.nomPrenomClasseExamen.services;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Session;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Statut;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.SessionRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final SessionServices sessionServices;
    private final SessionRepository sessionRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().contains("/FactureController") &&
                request.getServletPath().contains("/qrcode") && request.getServletPath().contains("/verify") ){
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7);
        try {
            userEmail = jwtService.extractUsername(token);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return; // Token malformé ou invalide
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<Session> sessionOpt = sessionRepository.findByToken(token);

            // Vérifier si session existe, est active et non expirée
            boolean sessionValide = sessionOpt.isPresent()
                    && sessionOpt.get().getStatut() == Statut.ACTIVE
                    && sessionOpt.get().getDateExpiration().isAfter(LocalDateTime.now());

            if (!sessionValide) {
                filterChain.doFilter(request, response);
                return;
            }

            // Authentification normale
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(token, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);

    }
}