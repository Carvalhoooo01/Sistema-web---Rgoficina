package rg_oficina_backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import rg_oficina_backend.security.jwt.AuthEntryPointJwt;
import rg_oficina_backend.security.jwt.AuthFilterToken;

/**
 * Configuração Global de Segurança.
 * Define as regras de CORS, Sessão e proteção de Rotas (Endpoints).
 * @author Gustavo Carvalho
 */
@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler; // Trata erros de autenticação (401)

    // Filtro customizado que intercepta o token em cada requisição
    @Autowired
    private AuthFilterToken authFilterToken;

    // Define o algoritmo de senha.
    // OBS: Usando NoOp (sem criptografia) para simplificação do ambiente de dev/teste.
    // Em produção, usaríamos BCryptPasswordEncoder.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // Expõe o gerenciador de autenticação para ser usado no AuthService
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Configuração de CORS: Permite que o Front-end converse com o Back-end
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Lista de origens permitidas (Seu Front no Netlify e Localhost)
        configuration.setAllowedOriginPatterns(Arrays.asList(
                "https://scos.netlify.app",
                "http://localhost:*",
                "http://127.0.0.1:*"
        ));

        // Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Headers permitidos (Authorization é essencial para o Token)
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true); // Permite envio de Cookies

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // A Cadeia de Filtros de Segurança (Onde as regras são aplicadas)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(Customizer.withDefaults()); // Aplica a config de CORS acima

        http.csrf(csrf -> csrf.disable()) // Desabilita CSRF (Padrão para APIs REST Stateless)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                // Define que NÃO haverá sessão no servidor (Stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Regras de acesso por URL
                .authorizeHttpRequests(auth -> auth
                        // Rotas Públicas (Login, Cadastro, Relatórios)
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/usuario/**").permitAll()
                        .requestMatchers("/relatorio/**").permitAll()
                        .requestMatchers("/relatorios/**").permitAll()
                        .requestMatchers("/error").permitAll()

                        // Todas as outras rotas exigem autenticação
                        .anyRequest().authenticated());

        // Adiciona nosso filtro JWT antes do filtro padrão do Spring
        http.addFilterBefore(authFilterToken, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}