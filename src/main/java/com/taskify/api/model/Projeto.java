package com.taskify.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_projetos")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProjeto;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(columnDefinition = "TEXT") //nullable = true Por padrão já vem TRUE
    private String descricao;

    @ManyToOne(optional = false) // campo iDusuário obrigatório
    @JoinColumn(name = "idUsuario") // relação da chave estrangeira com a classe usuário
    private Usuario usuario;
    
}
