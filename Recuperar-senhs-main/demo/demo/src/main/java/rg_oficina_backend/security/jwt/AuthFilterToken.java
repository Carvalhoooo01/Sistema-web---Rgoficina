/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package rg_oficina_backend.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import rg_oficina_backend.service.UserDetailServiceImpl;

/**
 * Filtro que intercepta todas as requisições para validar o Token JWT.
 * @author Gustavo Carvalho
 */
public class AuthFilterToken extends OncePerRequestFilter {

    // CORREÇÃO: Mudei o nome de 'logger' para 'log' para não conflitar com a classe pai
    private static final Logger log = LoggerFactory.getLogger(AuthFilterToken.class);

    @Autowired
    private JwtUtils jwtUtil;
    
    @Autowired
    private UserDetailServiceImpl userDetailService;
    
    @Override
    @SuppressWarnings("UseSpecificCatch")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getToken(request);
            if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
                
                String username = jwtUtil.getUsernameToken(jwt);
                
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            // CORREÇÃO: Usando 'log' em vez de 'logger'
            log.error("Não foi possível definir a autenticação do usuário: {}", e.getMessage(), e);
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String getToken(HttpServletRequest request) {
        String headerToken = request.getHeader("Authorization");
        
        if (StringUtils.hasText(headerToken) && headerToken.startsWith("Bearer ")) {
            return headerToken.substring(7);
        }
        return null;
    }
}