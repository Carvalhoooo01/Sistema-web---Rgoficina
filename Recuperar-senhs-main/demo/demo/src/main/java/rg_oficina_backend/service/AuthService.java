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
import rg_oficina_backend.service.UserDetailsImpl; // CONFIRA SE ESTE IMPORT ESTÁ CERTO

/**
 *
 * @author Gustavo Carvalho
 */
@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticatioManager;

    @Autowired
    private JwtUtils jwtUtils;

    // ALTERADO: de void para AcessoDTO
    public AcessoDTO login(AuthenticationDTO authDto, HttpServletResponse response) {
        // Cria mecanismo de credencial para o spring
        UsernamePasswordAuthenticationToken userAuth =
                new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword());

        // Autentica
        Authentication authentication = authenticatioManager.authenticate(userAuth);

        // Busca usuario logado (Cast para sua implementação de UserDetails)
        UserDetailsImpl userAuthenticate = (UserDetailsImpl)authentication.getPrincipal();

        // Gera o token
        String token = jwtUtils.generateTokenFromUserDetailsImpl(userAuthenticate);

        // --- Lógica do Cookie (Mantida) ---
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true); // Mais seguro contra XSS
        cookie.setSecure(false);  // Em localhost deve ser false. Em produção (HTTPS), true.
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        // cookie.setDomain("localhost"); // As vezes isso atrapalha testes locais, deixei comentado por precaução

        response.addCookie(cookie);

        // --- ALTERADO: Retorna o DTO com o Token ---
        return new AcessoDTO(token);
    }
}