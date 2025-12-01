package rg_oficina_backend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rg_oficina_backend.security.jwt.JwtUtils;

@RestController
@RequestMapping("/cadastro")
public class CadastroController {

    // Injeção da classe utilitária responsável por validar a criptografia do Token
    @Autowired
    private JwtUtils jwtUtils;

    // Endpoint utilitário para verificar se a sessão do usuário ainda é válida (True/False)
    @GetMapping("/verificar")
    public ResponseEntity<Boolean> checkAuth(HttpServletRequest request) {

        String token = null;

        // Estratégia 1: Tenta extrair o Token do cabeçalho 'Authorization' (Padrão Bearer)
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            token = headerAuth.substring(7); // Remove o prefixo "Bearer " para pegar apenas o hash
        }

        // Estratégia 2 (Fallback): Se não houver Header, busca o token nos Cookies da requisição
        if (token == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) { // Procura especificamente o cookie "token"
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // Valida o token encontrado (verifica assinatura e expiração)
        if (token != null && jwtUtils.validateJwtToken(token)) {
            return ResponseEntity.ok(true);
        }

        // Retorna falso caso nenhum token válido seja encontrado
        return ResponseEntity.ok(false);
    }
}