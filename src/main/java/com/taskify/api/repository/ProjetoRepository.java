package com.taskify.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.taskify.api.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    @Query("SELECT proj FROM tb_projetos proj WHERE proj.usuario.idUsuario = :idUsuario")//query customizada para buscar vários projetos pelo idUsuario
    Optional<List<Projeto>> findByUsuario(Long idUsuario);

}
