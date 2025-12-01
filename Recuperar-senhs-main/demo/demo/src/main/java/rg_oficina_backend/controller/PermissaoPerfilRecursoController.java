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

import rg_oficina_backend.dto.PermissaoPerfilRecursoDTO;
import rg_oficina_backend.service.PermissaoPerfilRecusoService;

/**
 * Controlador de Permissões Granulares.
 * Gerencia o nível mais detalhado de segurança, ligando um Perfil a um Recurso específico
 * (Ex: O Perfil 'Técnico' pode acessar o Recurso 'Tela de Relatórios'?).
 * @author Gustavo Carvalho
 */
@RestController
@RequestMapping(value = "/permissao-perfil-recurso")
@CrossOrigin
public class PermissaoPerfilRecursoController {

    @Autowired
    private PermissaoPerfilRecusoService permissaoPerfilRecursoService;

    // Lista todas as regras de acesso cadastradas no sistema
    @GetMapping
    public List<PermissaoPerfilRecursoDTO> listarTodos(){
        return permissaoPerfilRecursoService.listarTodos();
    }

    // Cria uma nova regra de permissão (Concede acesso a um recurso para um perfil)
    @PostMapping
    public void inserir(@RequestBody PermissaoPerfilRecursoDTO permissaoPerfilRecurso) {
        permissaoPerfilRecursoService.inserir(permissaoPerfilRecurso);
    }

    // Atualiza uma regra de permissão existente
    @PutMapping
    public PermissaoPerfilRecursoDTO alterar(@RequestBody PermissaoPerfilRecursoDTO permissaoPerfilRecurso) {
        return permissaoPerfilRecursoService.alterar(permissaoPerfilRecurso);
    }

    // Revoga o acesso (Remove a permissão de um perfil sobre um recurso)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        permissaoPerfilRecursoService.excluir(id);
        return ResponseEntity.ok().build();
    }
}