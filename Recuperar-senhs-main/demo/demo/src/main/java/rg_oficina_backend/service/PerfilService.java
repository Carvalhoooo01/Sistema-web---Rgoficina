package rg_oficina_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rg_oficina_backend.dto.PerfilDTO;
import rg_oficina_backend.entity.PerfilEntity;
import rg_oficina_backend.repository.PerfilRepository;

/**
 * Service de Perfis de Acesso.
 * Responsável pela regra de negócio dos cargos e pela conversão Entity <-> DTO.
 * @author Gustavo Carvalho
 */
@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    // Lista todos os perfis convertendo de Entidade para DTO usando Java Streams
    public List<PerfilDTO> listarTodos(){
        List<PerfilEntity> perfis = perfilRepository.findAll();

        // Uso de Stream API para mapeamento elegante e performático
        return perfis.stream().map(PerfilDTO::new).toList();
    }

    // Converte o DTO recebido em Entidade e salva no banco
    public void inserir(PerfilDTO perfil) {
        PerfilEntity perfilEntity = new PerfilEntity(perfil);
        perfilRepository.save(perfilEntity);
    }

    // Atualiza o perfil (converte, salva e retorna o novo DTO)
    public PerfilDTO alterar(PerfilDTO perfil) {
        PerfilEntity perfilEntity = new PerfilEntity(perfil);
        return new PerfilDTO(perfilRepository.save(perfilEntity));
    }

    // Busca o perfil pelo ID e remove do banco
    public void excluir(Long id) {
        // .get() extrai o valor do Optional retornado pelo repositório
        PerfilEntity perfil = perfilRepository.findById(id).get();
        perfilRepository.delete(perfil);
    }

    // Busca um perfil específico
    public PerfilDTO buscarPorId(Long id) {
        return new PerfilDTO(perfilRepository.findById(id).get());
    }
}