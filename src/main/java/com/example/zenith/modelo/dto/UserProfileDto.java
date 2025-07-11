package com.example.zenith.modelo.dto;

import com.example.zenith.utils.Enums.UserType;

public record UserProfileDto(
        Long id,
        String nome,
        String email,
        UserType tipo
) {}