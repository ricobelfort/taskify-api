package com.taskify.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.taskify.api.model.Projeto;
import com.taskify.api.repository.ProjetoRepository;

@RestController
@RequestMapping(value = "/projetos") //endpoint
public class ProjetoController {
    
    @PostMapping // Criar
    public @ResponseBody Projeto cadastrarProjeto(@RequestBody Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    @GetMapping // Listar
    public List<Projeto> listarProjetos() {
        return projetoRepository.findAll();
    }


    @PutMapping("/{id}") // Atualizar
    public Projeto atualizarProjeto(@PathVariable("id") Long idProjeto, @RequestBody Projeto projeto){

        Optional<Projeto> projetoExistente = projetoRepository.findById(idProjeto);

        if(projetoExistente.isPresent()){
            
            Projeto projetoObj = projetoExistente.get();

            // Atualizar os campos
            projetoObj.setNome(projeto.getNome());
            projetoObj.setDescricao(projeto.getDescricao());
            projetoObj.setUsuario(projeto.getUsuario());

            return projetoRepository.save(projetoObj);

        }

        return null;
    }

    @DeleteMapping("/{id}") // Deletar
    public void deletarProjetoPeloId(@PathVariable("id") Long id) {
        projetoRepository.deleteById(id);
    }

    @GetMapping("/usuario/{id}")
    public Optional<List<Projeto>> obterProjetosDeUmUsuario(@PathVariable("id") Long idUsuario) {
        return projetoRepository.findByUsuario(idUsuario);
    }    

    @Autowired
    private ProjetoRepository projetoRepository;
}
