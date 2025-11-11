package SCOS.springsecurity.service;

import SCOS.springsecurity.model.Cliente;
import SCOS.springsecurity.reporitory.ClienteRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService
{

    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository)
    {

        this.clienteRepository = clienteRepository;

    }

    public Cliente salvar(Cliente cliente)
    {

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
