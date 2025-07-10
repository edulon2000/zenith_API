package com.example.zenith.config.filter;

import com.example.zenith.modelo.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class GoogleIdTokenFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final GoogleIdTokenVerifier verifier;

    public GoogleIdTokenFilter(UserRepository userRepository, @Value("${google.client-id}") String googleClientId) {
        this.userRepository = userRepository;
        // Inicializa o verifier uma vez no construtor
        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Se não houver cabeçalho ou não começar com "Bearer ", continua sem autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String idTokenString = authHeader.substring(7);

        try {
            // Verifica o token com o Google
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                // Se o token for válido, extrai o email
                GoogleIdToken.Payload payload = idToken.getPayload();
                String email = payload.getEmail();

                // Busca o usuário no nosso banco de dados
                UserDetails userDetails = userRepository.findByEmail(email)
                        .orElse(null); // Retorna null se não encontrar

                if (userDetails != null) {
                    // Se o usuário existe, cria um objeto de autenticação e o coloca no contexto de segurança
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            // Se a verificação falhar, limpa o contexto de segurança
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}