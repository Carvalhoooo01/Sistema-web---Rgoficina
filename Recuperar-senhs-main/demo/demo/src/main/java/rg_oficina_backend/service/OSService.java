package rg_oficina_backend.service;

import rg_oficina_backend.dto.OSDTO;
import rg_oficina_backend.entity.OS;
import rg_oficina_backend.repository.OSRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OSService {

    private final ClienteService clienteService;
    private final OSRepository osRepository;

    public OSService(OSRepository osRepository, ClienteService clienteService) {
        this.osRepository = osRepository;
        this.clienteService = clienteService;
    }

    public OS salvar(OSDTO osDTO) {
        OS os = new OS();

        // --- CORREÇÃO AQUI ---
        // Se o seu projeto segue o padrão snake_case (com underline),
        // o record provavelmente tem o campo 'cliente_id'.
        // Trocamos .clienteId() por .cliente_id()

        os.setCliente_id(clienteService.buscar_por_id(osDTO.cliente_id()));
        os.setTipo(osDTO.tipo());
        os.setModelo(osDTO.modelo());
        os.setMarca(osDTO.marca());
        os.setN_serial(osDTO.n_serial());
        os.setDescricao(osDTO.descricao());
        os.setPrioridade(osDTO.prioridade());

        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        os.setData_abertura(hoje.format(formato));

        return osRepository.save(os);
    }

    public void excluir(OS os) {
        osRepository.deleteById(os.getId());
    }

    public OS buscar_por_id(Long id) {
        return osRepository.findById(id).orElse(null);
    }

    public List<OS> listar_todos() {
        return osRepository.findAll();
    }

    public OS editar(OS os, OSDTO osDTO) {

        // Correção na edição também: .cliente_id()
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

        return osRepository.findAllForRelatorio(dataIniFormatada, dataFimFormatada);
    }
}