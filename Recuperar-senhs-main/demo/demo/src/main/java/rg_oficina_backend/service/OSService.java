package rg_oficina_backend.service;

import rg_oficina_backend.dto.OSDTO;
import rg_oficina_backend.entity.OS;
import rg_oficina_backend.repository.OSRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class OSService
{

    private final ClienteService clienteService;
    private OSRepository osRepository;

    public OSService(OSRepository osRepository, ClienteService clienteService)
    {

        this.osRepository = osRepository;
        this.clienteService = clienteService;
    }

    public OS salvar(OSDTO osDTO)
    {

        OS os = new OS();

        /*
        * private Cliente cliente_id;

    private String cpf_cnpj;
    private String endereco;
    private String telefone_celular;
    private String telefone_fixo;
    private String tipo;
    private String modelo;
    private String marca;
    private String n_serial;
    private String descricao;
    private String prioridade;
    private Date data_abertura;
        * */

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

    public void excluir(OS os)
    {

        osRepository.deleteById(os.getId());

    }

    public OS buscar_por_id(Long id)
    {

        return osRepository.findById(id).orElse(null);

    }

    public List<OS> listar_todos()
    {

        return osRepository.findAll();

    }

    public OS editar(OS os)
    {

        return osRepository.save(os);

    }

    public List<OS> gerar_relatorio(LocalDate data_inicio, LocalDate data_fim)
    {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String dataIniFormatada = data_inicio.format(fmt);
        String dataFimFormatada = data_fim.format(fmt);

        return osRepository.findAllForRelatorio(dataIniFormatada, dataFimFormatada);

    }

}
