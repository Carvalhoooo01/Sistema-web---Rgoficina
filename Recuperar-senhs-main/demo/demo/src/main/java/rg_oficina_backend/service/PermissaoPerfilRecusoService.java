package rg_oficina_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rg_oficina_backend.dto.PermissaoPerfilRecursoDTO;
import rg_oficina_backend.entity.PermissaoPerfilRecursoEntity;
import rg_oficina_backend.repository.PermissaoPerfilRecursoRepository;

/**
 * Service de Permissões Granulares.
 * Gerencia a ligação lógica entre um Perfil e um Recurso (Autorização).
 * @author Gustavo Carvalho
 */
@Service
public class PermissaoPerfilRecusoService {

    @Autowired
    private PermissaoPerfilRecursoRepository permissaoPerfilRecursoRepository;

    // Lista todas as regras de permissão cadastradas no sistema
    public List<PermissaoPerfilRecursoDTO> listarTodos(){
        List<PermissaoPerfilRecursoEntity> permissaoPerfilRecusos = permissaoPerfilRecursoRepository.findAll();
        // Conversão Entity -> DTO usando Streams
        return permissaoPerfilRecusos.stream().map(PermissaoPerfilRecursoDTO::new).toList();
    }

    // Cria uma nova regra (Concede uma permissão)
    public void inserir(PermissaoPerfilRecursoDTO permissaoPerfilRecurso) {
        PermissaoPerfilRecursoEntity permissaoPerfilRecursoEntity = new PermissaoPerfilRecursoEntity(permissaoPerfilRecurso);
        permissaoPerfilRecursoRepository.save(permissaoPerfilRecursoEntity);
    }

    // Altera uma regra existente
    public PermissaoPerfilRecursoDTO alterar(PermissaoPerfilRecursoDTO permissaoPerfilRecurso) {
        PermissaoPerfilRecursoEntity permissaoPerfilRecursoEntity = new PermissaoPerfilRecursoEntity(permissaoPerfilRecurso);
        return new PermissaoPerfilRecursoDTO(permissaoPerfilRecursoRepository.save(permissaoPerfilRecursoEntity));
    }

    // Revoga uma permissão (Remove do banco)
    public void excluir(Long id) {
        PermissaoPerfilRecursoEntity permissaoPerfilRecurso = permissaoPerfilRecursoRepository.findById(id).get();
        permissaoPerfilRecursoRepository.delete(permissaoPerfilRecurso);
    }

    // Busca uma regra específica pelo ID
    public PermissaoPerfilRecursoDTO buscarPorId(Long id) {
        return new PermissaoPerfilRecursoDTO(permissaoPerfilRecursoRepository.findById(id).get());
    }
}