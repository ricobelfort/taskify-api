package com.taskify.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskify.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> { //herdar métodos das 4 operações para inserir no BD (crud)
    
    // Método finder
    Optional<Usuario> findByEmail(String email);
    Optional<List<Usuario>> findByNome(String nome);


    // To do next class: case sensitive request
}
