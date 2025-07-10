package com.example.zenith.modelo.model;

import com.example.zenith.utils.Enums.UserType;
import com.example.zenith.utils.Enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserType tipo; // CLIENTE ou PRESTADOR

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VerificationStatus statusVerificacao;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column
    private LocalDateTime dataAtualizacao;

    // Método para ser executado antes da primeira inserção
    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
        this.statusVerificacao = VerificationStatus.PENDENTE;
    }

    // Método para ser executado antes de uma atualização
    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Se for um PRESTADOR, ele tem o papel de PRESTADOR.
        if (this.tipo == UserType.PRESTADOR) {
            return List.of(new SimpleGrantedAuthority("ROLE_PRESTADOR"));
        }
        // Senão, é um CLIENTE com o papel de CLIENTE.
        return List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        // Nosso "username" é o email
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }



    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Futuramente, você pode usar a verificação de email ou status do usuário aqui
        return true;
    }
}