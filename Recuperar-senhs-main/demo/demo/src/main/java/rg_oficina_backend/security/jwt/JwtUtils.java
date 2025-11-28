/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

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
 * Utilitário para geração e validação de Tokens JWT.
 * @author Gustavo Carvalho
 */
@Component
public class JwtUtils {

    // Logger profissional para registrar erros de validação
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${projeto.jwtSecret}")
    private String jwtSecret;

    @Value("${projeto.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateTokenFromUserDetailsImpl(UserDetailsImpl userDetail) {
        return Jwts.builder()
                .setSubject(userDetail.getUsername()) // O username agora é o email
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(getSigninKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameToken(String token) {
        // Atualizado para parserBuilder() (sintaxe nova do JJWT)
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            // Atualizado para parserBuilder()
            Jwts.parserBuilder()
                    .setSigningKey(getSigninKey())
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch(MalformedJwtException e) {
            logger.error("Token JWT inválido: {}", e.getMessage());
        } catch(ExpiredJwtException e) {
            logger.error("Token JWT expirado: {}", e.getMessage());
        } catch(UnsupportedJwtException e) {
            logger.error("Token JWT não suportado: {}", e.getMessage());
        } catch(IllegalArgumentException e) {
            logger.error("String de claims JWT está vazia: {}", e.getMessage());
        }

        return false;
    }
}