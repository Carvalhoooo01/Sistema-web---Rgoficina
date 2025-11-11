package SCOS.springsecurity.service;

import SCOS.springsecurity.dto.ClienteDTO;
import SCOS.springsecurity.model.Cliente;
import SCOS.springsecurity.reporitory.ClienteRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class ClienteService
{

    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository)
    {

        this.clienteRepository = clienteRepository;

    }

    public Cliente salvar(ClienteDTO clienteDTO)
    {

        Cliente cliente = new Cliente();

        /*
        * private String nome;
    private String cpf_cnpj;
    private String email;
    private String telefone_fixo;
    private String telefone_celular;
    private String cep;
    private String estado;
    private String rua_numero;
    private String bairro;
    private Date data_cadastro;
        * */

        cliente.setNome(clienteDTO.nome());
        cliente.setEmail(clienteDTO.email());
        cliente.setCpf_cnpj(clienteDTO.cpf_cnpj());
        cliente.setTelefone_fixo(clienteDTO.telefone_fixo());
        cliente.setTelefone_celular(clienteDTO.telefone_celular());
        cliente.setCep(clienteDTO.cep());
        cliente.setEstado(clienteDTO.estado());
        cliente.setRua_numero(clienteDTO.rua_numero());
        cliente.setBairro(clienteDTO.bairro());

        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        cliente.setData_cadastro(hoje.format(formato));

        return clienteRepository.save(cliente);

    }

    public void excluir(Cliente cliente)
    {

        clienteRepository.deleteById(cliente.getId());

    }

    public Cliente buscar_por_id(Long id)
    {

        return clienteRepository.findById(id).orElse(null);

    }

    public List<Cliente> listar_todos()
    {

        return clienteRepository.findAll();

    }

    public boolean existe_igual(Cliente cliente)
    {

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnorePaths("id")
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);

        Example<Cliente> example = Example.of(cliente, matcher);

        return clienteRepository.exists(example);

    }

}
