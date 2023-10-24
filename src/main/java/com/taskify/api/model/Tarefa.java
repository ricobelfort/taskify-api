package com.taskify.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.taskify.api.constants.Prioridade;
import com.taskify.api.constants.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity(name = "tb_tarefas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarefa;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeCriacao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeConclusao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "idUsuario") // relação da chave estrangeira com a classe usuário
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idProjeto") // relação da chave estrangeira com a classe usuário
    private Projeto projeto;


}
