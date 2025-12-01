package rg_oficina_backend.security.jwt;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import rg_oficina_backend.service.UserDetailsImpl;

/**
 * Utilitário Criptográfico JWT.
 * Responsável por ASSINAR (gerar) e VALIDAR tokens de acesso.
 * @author Gustavo Carvalho
 */
@Component
public class JwtUtils {

    // Logger profissional: Essencial para debugar problemas de autenticação em produção
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    // Injeta a chave secreta e o tempo de expiração do application.properties
    @Value("${projeto.jwtSecret}")
    private String jwtSecret;

    @Value("${projeto.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Gera um Token JWT assinado com HS512.
     * @param userDetail Dados do usuário logado.
     * @return String do token (ex: eyJhbGciOiJIUzI1Ni...)
     */
    public String generateTokenFromUserDetailsImpl(UserDetailsImpl userDetail) {
        return Jwts.builder()
                .setSubject(userDetail.getUsername()) // Define o E-mail como identificador no token
                .setIssuedAt(new Date()) // Data de hoje
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs)) // Data atual + X milissegundos
                .signWith(getSigninKey(), SignatureAlgorithm.HS512) // Assina digitalmente
                .compact();
    }

    // Decodifica a chave secreta de Base64 para objeto criptográfico
    public Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extrai o E-mail de dentro do Token (operação inversa da geração)
    public String getUsernameToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Valida se o token é autêntico e não expirou.
     * @param authToken O token recebido na requisição.
     * @return true se válido, false se inválido.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            // Tenta ler e validar a assinatura do token
            Jwts.parserBuilder()
                    .setSigningKey(getSigninKey())
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch(MalformedJwtException e) {
            logger.error("Token JWT inválido (formato incorreto): {}", e.getMessage());
        } catch(ExpiredJwtException e) {
            logger.error("Token JWT expirado (tempo limite excedido): {}", e.getMessage());
        } catch(UnsupportedJwtException e) {
            logger.error("Token JWT não suportado: {}", e.getMessage());
        } catch(IllegalArgumentException e) {
            logger.error("String de claims JWT está vazia ou nula: {}", e.getMessage());
        }

        return false;
    }
}