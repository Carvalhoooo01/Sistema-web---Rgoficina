package rg_oficina_backend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rg_oficina_backend.security.jwt.JwtUtils;

@RestController
@RequestMapping("/cadastro")
public class CadastroController
{

    @GetMapping("/verificar")
    public ResponseEntity<Boolean> checkAuth(HttpServletRequest request) {

        JwtUtils jwtUtils = new JwtUtils();

        String token = "";

        for (Cookie cookie : request.getCookies()) {
            if ("token".equals(cookie.getName())) {
                token = cookie.getValue();
            }
        }

        if (token == null || !jwtUtils.validateJwtToken(token))
        {

            return ResponseEntity.ok(false);

        }

        return ResponseEntity.ok(true);

    }

}
