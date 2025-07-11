package com.example.zenith.modelo.Controller;

import com.example.zenith.modelo.dto.UserProfileDto;
import com.example.zenith.modelo.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getMyProfile(@AuthenticationPrincipal User currentUser) {
        // Converte a entidade User para o DTO UserProfileDto antes de enviar
        UserProfileDto userProfile = new UserProfileDto(
                currentUser.getId(),
                currentUser.getNome(),
                currentUser.getEmail(),
                currentUser.getTipo()
        );
        return ResponseEntity.ok(userProfile);
    }
}