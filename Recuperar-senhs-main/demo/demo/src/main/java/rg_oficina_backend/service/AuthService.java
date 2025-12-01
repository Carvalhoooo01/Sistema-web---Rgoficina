package rg_oficina_backend.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import rg_oficina_backend.dto.AcessoDTO;
import rg_oficina_backend.dto.AuthenticationDTO;
import rg_oficina_backend.security.jwt.JwtUtils;

/**
 * Serviço de Autenticação.
 * Responsável pela validação de credenciais e geração de tokens de segurança.
 * @author Gustavo Carvalho
 */
@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticatioManager; // Gerenciador oficial do Spring Security

    @Autowired
    private JwtUtils jwtUtils; // Classe utilitária que gera a string do Token

    public AcessoDTO login(AuthenticationDTO authDto, HttpServletResponse response) {

        // 1. Cria um objeto de autenticação com o usuário e senha recebidos do front
        UsernamePasswordAuthenticationToken userAuth =
                new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword());

        // 2. Realiza a autenticação efetiva.
        // O Spring vai no banco, verifica o hash da senha e retorna os dados do usuário.
        // Se a senha estiver errada, ele lança exceção (401 Bad Credentials) automaticamente aqui.
        Authentication authentication = authenticatioManager.authenticate(userAuth);

        // 3. Recupera os detalhes do usuário que acabou de logar com sucesso
        UserDetailsImpl userAuthenticate = (UserDetailsImpl)authentication.getPrincipal();

        // 4. Gera o Token assinado
        String token = jwtUtils.generateTokenFromUserDetailsImpl(userAuthenticate);

        // 5. CRIAÇÃO DO COOKIE SEGURO (Diferencial de Segurança)
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true); // Impede acesso via JavaScript (Proteção XSS)
        cookie.setSecure(true);   // Exige HTTPS (em produção)
        cookie.setPath("/");      // Disponível para toda a aplicação
        cookie.setMaxAge(3600);   // Validade de 1 hora

        // Adiciona o cookie na resposta HTTP
        response.addCookie(cookie);

        // Retorna o token também no corpo da resposta (JSON)
        return new AcessoDTO(token);
    }
}