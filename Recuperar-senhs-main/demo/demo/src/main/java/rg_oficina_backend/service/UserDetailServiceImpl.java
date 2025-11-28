package rg_oficina_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import rg_oficina_backend.entity.UsuarioEntity;
import rg_oficina_backend.repository.UsuarioRepository;

/**
 * Service para carregar detalhes do usuário durante a autenticação.
 * Implementa cache para evitar consultas duplicadas ao banco.
 *
 * @author Gustavo Carvalho
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Carrega um usuário pelo username (email).
     * Resultado é cacheado para evitar múltiplas consultas ao banco.
     *
     * @param username O email do usuário
     * @return UserDetails com as informações do usuário
     * @throws UsernameNotFoundException se o usuário não for encontrado
     */
    @Override
    @Cacheable(value = "users", key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔍 Buscando usuário no banco de dados: " + username);

        // O parâmetro 'username' aqui representa o email que o usuário digitou no login
        UsuarioEntity usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + username));

        System.out.println("✅ Usuário encontrado: " + usuario.getEmail());
        return UserDetailsImpl.build(usuario);
    }
}