package rg_oficina_backend.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import rg_oficina_backend.entity.UsuarioEntity;

/**
 *
 * @author Gustavo Carvalho
 */
public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    // Construtor atualizado: Removemos 'name' e 'username' separados
    public UserDetailsImpl(Long id, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(UsuarioEntity usuario) {
        return new UserDetailsImpl(
                usuario.getId(),
                // usuario.getNome(),  <-- REMOVIDO pois não existe mais na Entity
                // usuario.getLogin(), <-- REMOVIDO pois não existe mais na Entity
                usuario.getEmail(),
                usuario.getSenha(),
                new ArrayList<>() // Sem permissões por enquanto
        );
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    // --- MÉTODOS OBRIGATÓRIOS DO SPRING SECURITY ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    // PONTO CRUCIAL: O Spring Security pergunta "Qual é o nome de usuário?".
    // Nós respondemos: "É o email".
    @Override
    public String getUsername() {
        return email;
    }

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