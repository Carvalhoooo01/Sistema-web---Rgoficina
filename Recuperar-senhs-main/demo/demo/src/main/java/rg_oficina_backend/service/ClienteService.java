package rg_oficina_backend.service;

import org.springframework.web.bind.annotation.PostMapping;
import rg_oficina_backend.dto.ClienteDTO;
import rg_oficina_backend.entity.Cliente;
import rg_oficina_backend.entity.OS;
import rg_oficina_backend.repository.ClienteRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * Service de Clientes.
 * Responsável pela lógica de negócios, conversão DTO-Entity e validações (como duplicidade).
 */
@Service
public class ClienteService
{
    private ClienteRepository clienteRepository;

    // Injeção de dependência via construtor (Boa prática recomendada pelo Spring)
    public ClienteService(ClienteRepository clienteRepository)
    {
        this.clienteRepository = clienteRepository;
    }

    // Converte o DTO recebido do front para a Entidade do banco e define a data de cadastro automática
    public Cliente salvar(ClienteDTO clienteDTO)
    {
        Cliente cliente = new Cliente();

        // Mapeamento manual dos campos (DTO -> Entity)
        cliente.setNome(clienteDTO.nome());
        cliente.setEmail(clienteDTO.email());
        cliente.setCpf_cnpj(clienteDTO.cpf_cnpj());
        cliente.setTelefone_celular(clienteDTO.telefone_celular());
        cliente.setCep(clienteDTO.cep());
        cliente.setEstado(clienteDTO.estado());
        cliente.setRua_numero(clienteDTO.rua_numero());
        cliente.setBairro(clienteDTO.bairro());

        // Define a data de cadastro automaticamente para o dia de hoje (formato BR)
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
        // Retorna null se não encontrar (evita exceção imediata)
        return clienteRepository.findById(id).orElse(null);
    }

    public List<Cliente> listar_todos()
    {
        return clienteRepository.findAll();
    }

    // Busca customizada por string (Nome, CPF, etc.)
    public Cliente buscar_info(String info)
    {
        return clienteRepository.findByInfo(info);
    }

    // Atualiza os dados de um cliente existente
    public Cliente editar(Cliente cliente, ClienteDTO clienteDTO)
    {
        cliente.setNome(clienteDTO.nome());
        cliente.setEmail(clienteDTO.email());
        cliente.setCpf_cnpj(clienteDTO.cpf_cnpj());
        cliente.setTelefone_celular(clienteDTO.telefone_celular());
        cliente.setCep(clienteDTO.cep());
        cliente.setEstado(clienteDTO.estado());
        cliente.setRua_numero(clienteDTO.rua_numero());
        cliente.setBairro(clienteDTO.bairro());

        return clienteRepository.save(cliente);
    }

    // Método avançado: Verifica duplicidade usando "Query By Example"
    public boolean existe_igual(Cliente cliente)
    {
        // Configura o matcher para ignorar o ID e comparar todos os outros campos exatos
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnorePaths("id")
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);

        // Cria o exemplo (molde)
        Example<Cliente> example = Example.of(cliente, matcher);

        // O Spring verifica se algo no banco "encaixa" nesse molde
        return clienteRepository.exists(example);
    }

    // Prepara as datas e chama o repositório para o relatório
    public List<Cliente> gerar_relatorio(LocalDate data_inicio, LocalDate data_fim)
    {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String dataIniFormatada = data_inicio.format(fmt);
        String dataFimFormatada = data_fim.format(fmt);

        return clienteRepository.findAllForRelatorio(dataIniFormatada, dataFimFormatada);
    }

    public Cliente verificar_cpf_cnpj(String cpf_cnpj)
    {

        return clienteRepository.findByCpf_cnpj(cpf_cnpj);

    }

}