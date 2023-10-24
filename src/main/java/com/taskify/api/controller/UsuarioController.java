package com.taskify.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.taskify.api.dto.EnderecoDTO;
import com.taskify.api.model.Endereco;
import com.taskify.api.model.Usuario;
import com.taskify.api.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/usuarios") //endpoint //substitui todas as rotas dentro do controller /usuarios
public class UsuarioController {

    @PostMapping //método Create do CRUD
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuario));//altera o status 200 e retorna CREATED ao invés de 200 OK na criação de usuário
    }

    // @GetMapping //método listar padrão Read do CRUD
    // public List<Usuario> listarUsuarios() {
    //     return usuarioRepository.findAll();
    // }

    @GetMapping //método listar por páginas Read do CRUD 
    public ResponseEntity<Page<Usuario>> listarUsuarios(Pageable paginacao) { //import org.springframework.data.domain.Pageable;
        //public Page<Usuario> listarUsuarios(@PageableDefault(size = 2, page = 1, sort = "email", Direction(data.domain).DESC) Pageable paginacao; define o valor padrão de 
        // tamanho, páginas, ordenar por email e por ordem decrescente
        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.findAll(paginacao)); //passar na requisição http://localhost:8080/usuarios?size=2 
                                                    //define a qtd de páginas(resultados) exibidos
    }

    @GetMapping("/{id}") //método Read do CRUD através do ID do usuário // O Optional<Usuario> retira o erro (stack trace) padrão 500 e retorna o valor null 
    public ResponseEntity<Optional<Usuario>> obterUsuarioPeloId(@PathVariable("id") Long id) {
         Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

         if (usuarioExistente.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(usuarioExistente);
         }

         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}") //método Update do CRUD
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isPresent()){ //isPresent substitui usuarioExistente != null
            
            Usuario usuarioObj = usuarioExistente.get();

            // Atualizar os campos
            usuarioObj.setNome(usuario.getNome());
            usuarioObj.setSobrenome(usuario.getSobrenome());
            usuarioObj.setEmail(usuario.getEmail());
            usuarioObj.setSenha(usuario.getSenha());
            usuarioObj.setGenero(usuario.getGenero());

            return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.save(usuarioObj));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //.build pq não existe usuário, ao invés de ser NULL retorna status 404
    }

    @DeleteMapping("/{id}") //método Delete do CRUD
    public ResponseEntity<Void> deletarUsuarioPeloId(@PathVariable("id") Long id) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        usuarioRepository.deleteById(id);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }   
    
    @GetMapping("/email/{email}") //método Read do CRUD através do Email do usuário // O Optional<Usuario> retira o erro (stack trace) padrão 500 e retorna o valor null 
    public Optional<Usuario> obterUsuarioPeloEmail(@PathVariable("email") String email) {
        return usuarioRepository.findByEmail(email);
    }

    @GetMapping("/nome/{nome}") //método Read do CRUD através do Nome do usuário // O Optional<Usuario> retira o erro (stack trace) padrão 500 e retorna o valor null 
    public Optional<List<Usuario>> obterUsuarioPeloNome(@PathVariable("nome") String nome) {
        return usuarioRepository.findByNome(nome);
    }

    @GetMapping("/endereco/{cep}") //consumindo API externa
    public Endereco obterEnderecoPeloCep(@PathVariable("cep") String cep) {
        String url = String.format("https://viacep.com.br/ws/%s/json/", cep); //%s placeholder de string

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, Endereco.class);
    }

    @PostMapping("/endereco")
    public ResponseEntity<Usuario> salvarEnderecoDoUsuario(@RequestBody @Valid EnderecoDTO dto) {
        // 1. Verificar se o usuário existe
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(dto.getIdUsuario());

        
        if (usuarioExistente.isPresent()){
            // 2. Se o usuário existir, chamar a API do Via cep e obter os demais campos

            Endereco endereco = obterEnderecoPeloCep(dto.getCep());
            
            endereco.setNumero(dto.getNumero());
            endereco.setComplemento(dto.getComplemento());

            // 3. Usar o método set para atualizar o usuário

            Usuario usuario = usuarioExistente.get();

            usuario.setEndereco(endereco);

            // 4. Persistir os dados no banco

            return ResponseEntity.ok().body(usuarioRepository.save(usuario));
        }
        // 5. Se o usuário não existir, retorar 404

        return ResponseEntity.notFound().build();
    }

    @Autowired //Ponto de injeção instanciando a classe UsuarioRepository
    private UsuarioRepository usuarioRepository;

}
