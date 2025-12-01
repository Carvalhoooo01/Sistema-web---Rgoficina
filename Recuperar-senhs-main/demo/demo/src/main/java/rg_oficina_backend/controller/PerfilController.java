package rg_oficina_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rg_oficina_backend.dto.PerfilDTO;
import rg_oficina_backend.service.PerfilService;

/**
 * Controlador de Perfis de Acesso.
 * Gerencia os cargos/roles do sistema (ex: ADMIN, USER, TÉCNICO).
 * * @author Gustavo Carvalho
 */
@RestController
@RequestMapping(value = "/perfil")
@CrossOrigin // Permite que o Front-end (em outra porta/domínio) acesse estes recursos
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    // Lista todos os perfis disponíveis (Útil para preencher selects de cadastro)
    @GetMapping
    public List<PerfilDTO> listarTodos(){
        return perfilService.listarTodos();
    }

    // Cria um novo tipo de perfil de acesso
    @PostMapping
    public void inserir(@RequestBody PerfilDTO perfil){
        perfilService.inserir(perfil);
    }

    // Atualiza a descrição ou permissões de um perfil.
    // O ID deve estar contido dentro do objeto PerfilDTO enviado no corpo da requisição.
    @PutMapping
    public PerfilDTO alterar(@RequestBody PerfilDTO perfil){
        return perfilService.alterar(perfil);
    }

    // Remove um perfil do sistema pelo seu ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        perfilService.excluir(id);
        return ResponseEntity.ok().build();
    }

}