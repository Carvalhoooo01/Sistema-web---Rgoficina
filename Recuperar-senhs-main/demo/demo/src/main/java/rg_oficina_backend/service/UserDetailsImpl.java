package rg_oficina_backend.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import rg_oficina_backend.entity.UsuarioEntity;

/**
 * Implementação da Identidade do Usuário (Spring Security).
 * Funciona como um ADAPTADOR entre a Entidade do Banco (UsuarioEntity) e o Framework de Segurança.
 * @author Gustavo Carvalho
 */
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L; // Boa prática em classes Serializáveis

    private final Long id;
    private final String email;
    private final String password; // Senha Criptografada (Hash)

    private final Collection<? extends GrantedAuthority> authorities;

    // Construtor privado (usamos o método build para instanciar)
    public UserDetailsImpl(Long id, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Converte um UsuarioEntity (Banco) para UserDetailsImpl (Segurança).
     * @param usuario O usuário vindo do banco de dados.
     * @return O objeto de autenticação pronto.
     */
    public static UserDetailsImpl build(UsuarioEntity usuario) {
        return new UserDetailsImpl(
                usuario.getId(),
                usuario.getEmail(), // Aqui definimos que o identificador principal é o Email
                usuario.getSenha(),
                new ArrayList<>() // Lista de permissões (vazia por enquanto, expansível para RBAC)
        );
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    // --- MÉTODOS OBRIGATÓRIOS DA INTERFACE USERDETAILS ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * O Spring Security usa este método para saber quem é o usuário.
     * Sobrescrevemos para retornar o EMAIL, pois é assim que logamos no sistema.
     */
    @Override
    public String getUsername() {
        return email;
    }

    // Configurações de validade da conta (Retornamos true para simplificar: a conta nunca expira)

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}