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
 * Service de Detalhes do Usuário (Spring Security).
 * Responsável por buscar o usuário no banco e adaptar para o formato de segurança.
 *
 * @author Gustavo Carvalho
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Método obrigatório da interface UserDetailsService.
     * O Spring chama esse método automaticamente toda vez que alguém tenta fazer login.
     *
     * @param username O email que o usuário digitou na tela de login.
     * @return UserDetails Objeto padrão de segurança com senha e permissões.
     * @throws UsernameNotFoundException Caso o e-mail não exista no banco.
     */
    @Override
    @Cacheable(value = "users", key = "#username") // OTIMIZAÇÃO: Guarda o resultado em memória (Cache)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Log para monitoramento (útil para ver se o cache está funcionando: se aparecer muitas vezes, o cache falhou)
        System.out.println("Buscando usuário no banco de dados: " + username);

        // Busca no banco de dados pelo e-mail
        UsuarioEntity usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + username));

        System.out.println("Usuário encontrado: " + usuario.getEmail());

        // Converte a Entidade do banco para o objeto UserDetails que o Spring Security exige
        return UserDetailsImpl.build(usuario);
    }
}