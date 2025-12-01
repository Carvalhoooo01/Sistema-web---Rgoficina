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

import rg_oficina_backend.dto.UsuarioDTO;
import rg_oficina_backend.service.UsuarioService;

/**
 * Controlador de Usuários Administrativos.
 * Gerencia o CRUD dos colaboradores que terão acesso ao sistema.
 * @author Gustavo Carvalho
 */
@RestController
@RequestMapping(value = "/usuario")
@CrossOrigin
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Lista todos os usuários do sistema, retornando DTOs para ocultar dados sensíveis (como senhas)
    @GetMapping
    public List<UsuarioDTO> listarTodos(){
        return usuarioService.listarTodos();
    }

    // Cria um novo usuário administrativo.
    // Aciona o fluxo de "Novo Usuário" que gera senha aleatória e envia por e-mail.
    @PostMapping
    public void inserir(@RequestBody UsuarioDTO usuario) {
        usuarioService.inserirNovoUsuario(usuario);
    }

    // Atualiza dados cadastrais do usuário (Nome, E-mail, etc.)
    @PutMapping
    public UsuarioDTO alterar(@RequestBody UsuarioDTO usuario) {
        return usuarioService.alterar(usuario);
    }

    // Remove um usuário do sistema pelo ID.
    // Exemplo de chamada: DELETE http://localhost:8080/usuario/3
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id){
        usuarioService.excluir(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("username")
    public ResponseEntity<?> username()
    {
        return null;
    }

}