package rg_oficina_backend.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Ponto de Entrada de Erros de Autenticação.
 * Personaliza a resposta quando um usuário não autenticado tenta acessar uma rota protegida.
 * @author Gustavo Carvalho
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    // Método disparado automaticamente pelo Spring Security quando ocorre um erro 401
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        // 1. Configura o cabeçalho para dizer ao navegador: "Estou te mandando um JSON"
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // 2. Define o código de erro HTTP 401 (Não Autorizado)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 3. Monta o objeto com os detalhes do erro
        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage()); // Ex: "Bad credentials" ou "Full authentication is required"
        body.put("path", request.getServletPath()); // Qual URL o usuário tentou acessar

        // 4. Converte o Map Java para uma String JSON e escreve na resposta
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}