package rg_oficina_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rg_oficina_backend.dto.PerfilUsuarioDTO;
import rg_oficina_backend.entity.PerfilUsuarioEntity;
import rg_oficina_backend.repository.PerfilUsuarioRepository;

/**
 * Service de Vínculos (Perfil <-> Usuário).
 * Gerencia a tabela que conecta usuários aos seus cargos.
 * @author Gustavo Carvalho
 */
@Service
public class PerfilUsuarioService {

    @Autowired
    private PerfilUsuarioRepository perfilUsuarioRepository;

    // Lista todas as associações existentes no banco
    public List<PerfilUsuarioDTO> listarTodos(){
        List<PerfilUsuarioEntity> perfilUsuarios = perfilUsuarioRepository.findAll();
        // Conversão otimizada usando Streams
        return perfilUsuarios.stream().map(PerfilUsuarioDTO::new).toList();
    }

    // Cria um novo vínculo (Atribui um cargo a um usuário)
    public void inserir(PerfilUsuarioDTO perfilUsuario) {
        PerfilUsuarioEntity perfilUsuarioEntity = new PerfilUsuarioEntity(perfilUsuario);
        perfilUsuarioRepository.save(perfilUsuarioEntity);
    }

    // Altera um vínculo existente (Ex: Trocar o cargo de um usuário)
    public PerfilUsuarioDTO alterar(PerfilUsuarioDTO perfilUsuario) {
        PerfilUsuarioEntity perfilUsuarioEntity = new PerfilUsuarioEntity(perfilUsuario);
        return new PerfilUsuarioDTO(perfilUsuarioRepository.save(perfilUsuarioEntity));
    }

    // Remove o vínculo (O usuário perde o cargo, mas não é excluído do sistema)
    public void excluir(Long id) {
        PerfilUsuarioEntity vinculo = perfilUsuarioRepository.findById(id).get();
        perfilUsuarioRepository.delete(vinculo);
    }

    // Busca um vínculo específico pelo ID
    public PerfilUsuarioDTO buscarPorId(Long id) {
        return new PerfilUsuarioDTO(perfilUsuarioRepository.findById(id).get());
    }
}