package com.example.zenith.modelo.Controller;

import com.example.zenith.modelo.dto.GoogleTokenDto;
import com.example.zenith.modelo.model.User;
import com.example.zenith.modelo.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/google")
    public ResponseEntity<?> syncUser(@RequestBody GoogleTokenDto googleTokenDto) {
        try {
            User user = authService.syncUserWithGoogle(googleTokenDto);
            // Retorna os dados do usuário sincronizado
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na sincronização com o Google: " + e.getMessage());
        }
    }
}