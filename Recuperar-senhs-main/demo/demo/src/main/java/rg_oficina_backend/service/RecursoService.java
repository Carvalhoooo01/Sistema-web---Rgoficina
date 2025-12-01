package rg_oficina_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rg_oficina_backend.dto.RecursoDTO;
import rg_oficina_backend.entity.RecursoEntity;
import rg_oficina_backend.repository.RecursoRepository;

/**
 * Service de Recursos.
 * Gerencia o cadastro das funcionalidades (Telas/Menus) que existem no sistema.
 * @author Gustavo Carvalho
 */
@Service
public class RecursoService {

    @Autowired
    private RecursoRepository recursoRepository;

    // Retorna a lista de todas as funcionalidades cadastradas
    public List<RecursoDTO> listarTodos(){
        List<RecursoEntity> recursos = recursoRepository.findAll();
        // Conversão DTO usando Streams
        return recursos.stream().map(RecursoDTO::new).toList();
    }

    // Cadastra uma nova funcionalidade no sistema
    public void inserir(RecursoDTO recurso) {
        RecursoEntity recursoEntity = new RecursoEntity(recurso);
        recursoRepository.save(recursoEntity);
    }

    // Atualiza a descrição ou chave de uma funcionalidade
    public RecursoDTO alterar(RecursoDTO recurso) {
        RecursoEntity recursoEntity = new RecursoEntity(recurso);
        return new RecursoDTO(recursoRepository.save(recursoEntity));
    }

    // Remove uma funcionalidade do sistema
    public void excluir(Long id) {
        RecursoEntity recurso = recursoRepository.findById(id).get();
        recursoRepository.delete(recurso);
    }

    // Busca uma funcionalidade pelo ID
    public RecursoDTO buscarPorId(Long id) {
        return new RecursoDTO(recursoRepository.findById(id).get());
    }
}