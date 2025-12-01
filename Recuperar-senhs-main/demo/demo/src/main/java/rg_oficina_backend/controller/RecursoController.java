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

import rg_oficina_backend.dto.RecursoDTO;
import rg_oficina_backend.service.RecursoService;

/**
 * Controlador de Recursos.
 * Gerencia os ativos do sistema que podem ser protegidos (Telas, Menus, URLs, Botões).
 * É a base para a montagem de menus dinâmicos no Front-end.
 * @author Gustavo Carvalho
 */
@RestController
@RequestMapping(value = "/recurso")
@CrossOrigin
public class RecursoController {

    @Autowired
    private RecursoService recursoService;

    // Lista todas as funcionalidades cadastradas no sistema
    @GetMapping
    public List<RecursoDTO> listarTodos(){
        return recursoService.listarTodos();
    }

    // Cadastra um novo recurso (ex: Nova tela "Estoque" ou endpoint "/api/estoque")
    @PostMapping
    public void inserir(@RequestBody RecursoDTO recurso) {
        recursoService.inserir(recurso);
    }

    // Atualiza a descrição ou a chave identificadora de um recurso
    @PutMapping
    public RecursoDTO alterar(@RequestBody RecursoDTO recurso) {
        return recursoService.alterar(recurso);
    }

    // Remove um recurso do sistema (Cuidado: deve-se remover as permissões vinculadas antes)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        recursoService.excluir(id);
        return ResponseEntity.ok().build();
    }
}