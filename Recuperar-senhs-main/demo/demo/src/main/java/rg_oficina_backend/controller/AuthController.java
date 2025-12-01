package rg_oficina_backend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rg_oficina_backend.dto.AcessoDTO;
import rg_oficina_backend.dto.AuthenticationDTO;
import rg_oficina_backend.dto.UsuarioDTO;
import rg_oficina_backend.dto.UsuarioRecuperarDTO;
import rg_oficina_backend.service.AuthService;
import rg_oficina_backend.service.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService;

    // Realiza o login do usuario verificando as credenciais e retornando o token de acesso
    @PostMapping(value = "/login")
    public ResponseEntity<AcessoDTO> login(@RequestBody AuthenticationDTO authDto, HttpServletResponse response){

        AcessoDTO acessoDto = authService.login(authDto, response);

        return ResponseEntity.ok(acessoDto);

    }

    // Cadastra um novo usuário no banco de dados e envia a senha gerada para o e-mail cadastrado
    @PostMapping(value = "/novoUsuario")
    public ResponseEntity<String> inserirNovoUsuario(@RequestBody UsuarioDTO novoUsuario){
        usuarioService.inserirNovoUsuario(novoUsuario);
        return ResponseEntity.ok("Usuário cadastrado com sucesso! Verifique sua senha no e-mail: " + novoUsuario.getEmail());
    }

    // Inicia o processo de recuperação de senha enviando instruções para o e-mail do usuário
    @PostMapping(value = "/esqueci-senha")
    public ResponseEntity<String> recuperarSenha(@RequestBody UsuarioRecuperarDTO dto) {
        String resultado = usuarioService.recuperarSenha(dto.email());
        return ResponseEntity.ok(resultado);
    }

    // Verifica o cadastro do usuário através do link enviado por e-mail (validação de UUID)
    @GetMapping(value = "/verificarCadastro/{uuid}")
    public String verificarCadastro(@PathVariable("uuid") String uuid) {
        return usuarioService.verificarCadastro(uuid);
    }

    // Realiza o logout do usuário limpando os cookies de autenticação do navegador
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {

        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return ResponseEntity.ok("Logout realizado");
    }

}