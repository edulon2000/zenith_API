package com.example.zenith.modelo.service;

import com.example.zenith.modelo.dto.GoogleTokenDto;
import com.example.zenith.modelo.model.User;

public interface AuthService {
    // Apenas este método é necessário para o fluxo de autenticação
    User syncUserWithGoogle(GoogleTokenDto googleTokenDto) throws Exception;
}