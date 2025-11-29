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

    // MELHORIA 1: Usamos Autowired para o Spring carregar a configuração correta (secret key)
    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/verificar")
    public ResponseEntity<Boolean> checkAuth(HttpServletRequest request) {

        String token = null;

        // PASSO 1: Tenta pegar o Token do Header (Padrão Bearer) - É o que seu front está mandando agora
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            token = headerAuth.substring(7); // Remove a palavra "Bearer " e pega só o código
        }

        // PASSO 2: Se não achou no Header, tenta nos Cookies (Fallback)
        // CORREÇÃO DO ERRO: Adicionamos a verificação (request.getCookies() != null) antes do loop
        if (token == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) { // Verifica se o cookie se chama "token"
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // PASSO 3: Valida o Token encontrado
        if (token != null && jwtUtils.validateJwtToken(token)) {
            return ResponseEntity.ok(true);
        }

        // Se chegou aqui, não tem token ou é inválido
        return ResponseEntity.ok(false);
    }
}