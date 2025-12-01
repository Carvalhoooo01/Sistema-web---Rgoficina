package rg_oficina_backend.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import rg_oficina_backend.service.UserDetailServiceImpl;

/**
 * Filtro de Segurança JWT.
 * Intercepta TODA requisição para validar o Token e autenticar o usuário no Contexto do Spring.
 * @author Gustavo Carvalho
 */
@Component // Transforma a classe em um Bean gerenciado, permitindo injeção de dependências (@Autowired)
public class AuthFilterToken extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthFilterToken.class);

    @Autowired
    private JwtUtils jwtUtil; // Ferramenta para validar a assinatura do token

    @Autowired
    private UserDetailServiceImpl userDetailService; // Busca o usuário no banco

    /**
     * Método principal do filtro. Executado a cada requisição HTTP.
     */
    @Override
    @SuppressWarnings("UseSpecificCatch")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // 1. Extrai o token do cabeçalho (remove o "Bearer ")
            String jwt = getToken(request);

            // 2. Valida a assinatura e validade do token
            if (jwt != null && jwtUtil.validateJwtToken(jwt)) {

                // 3. Extrai o email (username) de dentro do token
                String username = jwtUtil.getUsernameToken(jwt);

                // 4. Busca os dados atualizados do usuário no banco (checagem de segurança adicional)
                UserDetails userDetails = userDetailService.loadUserByUsername(username);

                // 5. Cria o objeto de autenticação do Spring Security
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. Define o usuário como "Logado" para esta requisição
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            log.error("Não foi possível definir a autenticação do usuário: {}", e.getMessage(), e);
        }

        // Continua o fluxo da requisição (vai para o próximo filtro ou para o Controller)
        filterChain.doFilter(request, response);
    }

    // Método auxiliar para limpar a string do header Authorization
    private String getToken(HttpServletRequest request) {
        String headerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(headerToken) && headerToken.startsWith("Bearer ")) {
            return headerToken.substring(7); // Remove os 7 primeiros caracteres ("Bearer ")
        }
        return null;
    }
}