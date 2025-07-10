package com.example.zenith.modelo.dto;

import com.example.zenith.utils.Enums.UserType;

public record RegisterDTO(
        String nome,
        String email,
        String senha,
        UserType tipo
) {}