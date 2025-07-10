package com.example.zenith.config;

import com.example.zenith.config.filter.GoogleIdTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final GoogleIdTokenFilter googleIdTokenFilter;

    public SecurityConfig(GoogleIdTokenFilter googleIdTokenFilter) {
        this.googleIdTokenFilter = googleIdTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // O endpoint de "registro/login" com Google é público
                        .requestMatchers("/auth/google").permitAll()
                        // Todas as outras requisições exigem autenticação
                        .anyRequest().authenticated()
                )
                // Adiciona nosso novo filtro para rodar antes dos filtros de segurança padrão
                .addFilterBefore(googleIdTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}