package com.example.zenith.modelo.repository;

import com.example.zenith.modelo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca um usuário pelo seu endereço de e-mail.
     * O Spring Data JPA implementa este método automaticamente.
     *
     * @param email O email do usuário a ser buscado.
     * @return um Optional contendo o User se encontrado, ou um Optional vazio caso contrário.
     */
    Optional<User> findByEmail(String email);

}