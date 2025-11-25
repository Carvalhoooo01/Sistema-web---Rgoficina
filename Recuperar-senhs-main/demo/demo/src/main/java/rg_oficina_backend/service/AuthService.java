package rg_oficina_backend.service;

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

    public AcessoDTO login(AuthenticationDTO authDto) {
        // Cria mecanismo de credencial para o spring
        UsernamePasswordAuthenticationToken userAuth = 
                new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword());
        
        // Prepara mecanismo para autenticacao
        // Se a senha estiver errada, o Spring lança uma exceção aqui automaticamente (401 Unauthorized)
        Authentication authentication = authenticatioManager.authenticate(userAuth);
        
        // Busca usuario logado
        UserDetailsImpl userAuthenticate = (UserDetailsImpl)authentication.getPrincipal();
        
        String token = jwtUtils.generateTokenFromUserDetailsImpl(userAuthenticate);
        
        return new AcessoDTO(token);
    }
}