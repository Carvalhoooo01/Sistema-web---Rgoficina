package SCOS.springsecurity.service;

import SCOS.springsecurity.dto.OSDTO;
import SCOS.springsecurity.model.OS;
import SCOS.springsecurity.reporitory.OSRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OSService
{

    private OSRepository osRepository;

    public OSService(OSRepository osRepository)
    {

        this.osRepository = osRepository;

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

        os.setCpf_cnpj(osDTO.cpf_cnpj());
        os.setEndereco(osDTO.endereco());
        os.setTelefone_celular(osDTO.telefone_celular());
        os.setTelefone_fixo(osDTO.telefone_fixo());
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


}
