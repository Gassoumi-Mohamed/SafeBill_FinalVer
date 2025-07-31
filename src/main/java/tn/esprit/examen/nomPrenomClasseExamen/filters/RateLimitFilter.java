package tn.esprit.examen.nomPrenomClasseExamen.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.github.bucket4j.*;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter implements Filter {

    // Map pour stocker les "seaux" (buckets) par IP
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();

        // Exclure Swagger et docs de l'API
        if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui.html") || path.startsWith("/webjars")) {
            chain.doFilter(request, response);
            return;
        }

        String ip = httpRequest.getRemoteAddr();
        Bucket bucket = buckets.computeIfAbsent(ip, this::createNewBucket);

        // Log des jetons restants
        System.out.println("IP: " + ip + " | Jetons restants: " + bucket.getAvailableTokens());

        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response); // Autorisé
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(429); // Too Many Requests
            httpResponse.getWriter().write("Rate limit exceeded. Try again later.");
        }
    }

    // Crée un seau avec 5 requêtes par minute
    private Bucket createNewBucket(String ip) {
        Refill refill = Refill.greedy(5, Duration.ofMinutes(1)); // Recharge de 5 jetons toutes les 60s
        Bandwidth limit = Bandwidth.classic(5, refill);
        return Bucket.builder().addLimit(limit).build();
    }
}
