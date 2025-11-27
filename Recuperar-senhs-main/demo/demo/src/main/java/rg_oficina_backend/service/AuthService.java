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
 *
 * @author Gustavo Carvalho
 */
@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticatioManager;

    @Autowired
    private JwtUtils jwtUtils;

    public void login(AuthenticationDTO authDto, HttpServletResponse response) {
        // Cria mecanismo de credencial para o spring
        UsernamePasswordAuthenticationToken userAuth = 
                new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword());
        
        // Prepara mecanismo para autenticacao
        // Se a senha estiver errada, o Spring lança uma exceção aqui automaticamente (401 Unauthorized)
        Authentication authentication = authenticatioManager.authenticate(userAuth);
        
        // Busca usuario logado
        UserDetailsImpl userAuthenticate = (UserDetailsImpl)authentication.getPrincipal();
        
        String token = jwtUtils.generateTokenFromUserDetailsImpl(userAuthenticate);

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        cookie.setDomain("localhost");

        response.addCookie(cookie);

    }
}