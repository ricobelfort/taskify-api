package com.taskify.api.model;

import com.taskify.api.constants.Genero;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @ chama-se Anotação
@Data // Dependência Lombok cria automaticamente os Getters e os Setters
@NoArgsConstructor // Dependência Lombok cria o construtor padrão que é exigido no Spring Boot
@AllArgsConstructor // Dependência Lombok cria os construtores com os atributos da classe
@Entity(name = "tb_usuarios") // Cria a tabela no banco de dados com nome específicio, por padrão cria tabela c/ nome Usuário
public class Usuario {
    
    @Id //usar a biblioteca jakarta - define a chave primária da tabela do atributo abaixo
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false, length = 50) //nullable - Declaração para o atributo ser um campo obrigatório na tabela
    private String nome;

    @Column(nullable = false, length = 150)
    private String sobrenome;

    @Column(nullable = false, unique = true) //length padrão na tabela VARCHAR de 255 caracteres --- unique p/ que o email seja único
    private String email;

    @Column(nullable = false, length = 10)
    private String senha;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Embedded //inclui os atributos da classe endereco na Usuário
    private Endereco endereco;

}
