package com.example.zenith.modelo.service.serviceImpl;

import com.example.zenith.modelo.dto.GoogleTokenDto;
import com.example.zenith.modelo.model.User;
import com.example.zenith.modelo.repository.UserRepository;
import com.example.zenith.modelo.service.AuthService;
import com.example.zenith.utils.Enums.UserType;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final GoogleIdTokenVerifier verifier;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, @Value("${google.client-id}") String googleClientId) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();
    }

    @Override
    public User syncUserWithGoogle(GoogleTokenDto googleTokenDto) throws Exception {
        GoogleIdToken idToken = verifier.verify(googleTokenDto.idToken());
        if (idToken == null) {
            throw new IllegalArgumentException("Token do Google inválido!");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();
        String email = payload.getEmail();
        String nome = (String) payload.get("name");

        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setNome(nome);
                    newUser.setTipo(UserType.CLIENTE);
                    newUser.setSenha(passwordEncoder.encode("google-default-pwd-" + System.nanoTime()));
                    return userRepository.save(newUser);
                });
    }
}
