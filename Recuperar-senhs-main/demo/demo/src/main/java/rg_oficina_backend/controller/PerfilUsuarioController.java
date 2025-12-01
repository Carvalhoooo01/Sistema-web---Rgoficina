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

import rg_oficina_backend.dto.PerfilUsuarioDTO;
import rg_oficina_backend.service.PerfilUsuarioService;

/**
 * Controlador de Vínculos (PerfilUsuario).
 * Responsável por associar um Usuário a um Perfil de acesso (Ex: Dizer que o usuário X é ADMIN).
 * @author Gustavo Carvalho
 */
@RestController
@RequestMapping(value = "/perfil-usuario")
@CrossOrigin
public class PerfilUsuarioController {

    @Autowired
    private PerfilUsuarioService perfilUsuarioService;

    // Lista todas as associações de permissões existentes no banco
    @GetMapping("/listar")
    public List<PerfilUsuarioDTO> listarTodos(){
        return perfilUsuarioService.listarTodos();
    }

    // Cria o vínculo: Conecta um Usuário específico a um Perfil específico
    @PostMapping
    public void inserir(@RequestBody PerfilUsuarioDTO perfilUsuario) {
        perfilUsuarioService.inserir(perfilUsuario);
    }

    // Atualiza um vínculo existente (Ex: Mudar o nível de acesso de um usuário)
    @PutMapping
    public PerfilUsuarioDTO alterar(@RequestBody PerfilUsuarioDTO perfilUsuario) {
        return perfilUsuarioService.alterar(perfilUsuario);
    }

    // Remove o vínculo (Revoga a permissão do usuário, sem apagar o usuário do sistema)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        perfilUsuarioService.excluir(id);
        return ResponseEntity.ok().build();
    }
}