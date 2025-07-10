package com.example.zenith.modelo.dto;
import com.fasterxml.jackson.annotation.JsonProperty; // Adicione esta importação

public record GoogleTokenDto(
        @JsonProperty("id_token") String idToken
) {}