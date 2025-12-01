package rg_oficina_backend.service;

import rg_oficina_backend.dto.OSDTO;
import rg_oficina_backend.entity.OS;
import rg_oficina_backend.repository.OSRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Service de Ordem de Serviço.
 * Responsável pelo fluxo principal do sistema.
 * Demonstra a interação entre serviços (OS consome Cliente).
 */
@Service
public class OSService {

    // Dependências imutáveis (Boas práticas de injeção)
    private final ClienteService clienteService;
    private final OSRepository osRepository;

    // Injeção via construtor (Facilita testes unitários e evita NullPointerException)
    public OSService(OSRepository osRepository, ClienteService clienteService) {
        this.osRepository = osRepository;
        this.clienteService = clienteService;
    }

    public OS salvar(OSDTO osDTO) {
        OS os = new OS();

        // INTEGRAÇÃO ENTRE SERVICES:
        // Converte o ID numérico (vindo do JSON) no Objeto Cliente real (vindo do banco)
        os.setCliente_id(clienteService.buscar_por_id(osDTO.cliente_id()));

        // Mapeamento dos campos restantes
        os.setTipo(osDTO.tipo());
        os.setModelo(osDTO.modelo());
        os.setMarca(osDTO.marca());
        os.setN_serial(osDTO.n_serial());
        os.setDescricao(osDTO.descricao());
        os.setPrioridade(osDTO.prioridade());

        // Define data automática (Segurança/Auditoria)
        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        os.setData_abertura(hoje.format(formato));

        return osRepository.save(os);
    }

    public void excluir(OS os) {
        osRepository.deleteById(os.getId());
    }

    public OS buscar_por_id(Long id) {
        // Retorna a OS ou null se não encontrar
        return osRepository.findById(id).orElse(null);
    }

    public List<OS> listar_todos() {
        return osRepository.findAll();
    }

    public OS editar(OS os, OSDTO osDTO) {
        // Atualiza o vínculo do cliente caso tenha sido alterado na edição
        os.setCliente_id(clienteService.buscar_por_id(osDTO.cliente_id()));

        os.setTipo(osDTO.tipo());
        os.setModelo(osDTO.modelo());
        os.setMarca(osDTO.marca());
        os.setN_serial(osDTO.n_serial());
        os.setDescricao(osDTO.descricao());
        os.setPrioridade(osDTO.prioridade());

        return osRepository.save(os);
    }

    public List<OS> gerar_relatorio(LocalDate data_inicio, LocalDate data_fim) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String dataIniFormatada = data_inicio.format(fmt);
        String dataFimFormatada = data_fim.format(fmt);

        // Chama a query customizada no Repository
        return osRepository.findAllForRelatorio(dataIniFormatada, dataFimFormatada);
    }
}