package rg_oficina_backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rg_oficina_backend.entity.UsuarioEntity;

/**
 * @author Gustavo Carvalho
 */
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{

    // APENAS ESTE FICA.
    // O sistema agora vai usar o E-mail tanto para Logar quanto para Recuperar Senha.
    Optional<UsuarioEntity> findByEmail(String email);

}