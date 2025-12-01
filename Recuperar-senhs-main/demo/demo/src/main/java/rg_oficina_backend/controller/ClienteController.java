package rg_oficina_backend.controller;

import rg_oficina_backend.dto.ClienteDTO;
import rg_oficina_backend.dto.InfoDTO;
import rg_oficina_backend.dto.RelatorioDTO;
import rg_oficina_backend.entity.Cliente;
import rg_oficina_backend.entity.OS;
import rg_oficina_backend.repository.ClienteRepository;
import rg_oficina_backend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/**
 * Controlador de Clientes (ClienteController).
 * Responsável pelo CRUD (Criar, Ler, Atualizar, Deletar) de clientes
 * e pela geração de relatórios baseados em datas.
 */
@RestController
@RequestMapping("/cliente")
public class ClienteController
{

    @Autowired
    private ClienteService clienteService;

    // Busca um cliente específico pelo seu ID único
    @GetMapping("/buscar_id/{id}")
    public Cliente findById(@PathVariable Long id)
    {
        return clienteService.buscar_por_id(id);
    }

    // Realiza busca de cliente por informações variadas (Nome, CPF, etc) contidas no DTO
    @PostMapping("/buscar_info")
    public Cliente buscarInfo(@RequestBody InfoDTO infoDTO)
    {
        return clienteService.buscar_info(infoDTO.info());
    }

    // Retorna a lista completa de clientes cadastrados no banco
    @GetMapping("/listar")
    public List<Cliente> listar_todos()
    {
        return clienteService.listar_todos();
    }

    // Recebe um DTO com dados do cliente e persiste no banco de dados
    @PostMapping("/salvar")
    public void salvar(@RequestBody ClienteDTO clienteDTO)
    {
        clienteService.salvar(clienteDTO);
    }

    // Atualiza os dados de um cliente existente. Verifica se ele existe antes de editar.
    @PutMapping("/editar/{id}")
    public ResponseEntity<Void> editar(@RequestBody ClienteDTO clienteDTO, @PathVariable Long id)
    {
        Cliente clienteSalvo = clienteService.buscar_por_id(id);

        if(clienteSalvo == null)
        {
            return ResponseEntity.notFound().build(); // Retorna 404 se não achar
        }

        clienteService.editar(clienteSalvo, clienteDTO);

        return ResponseEntity.noContent().build(); // Retorna 204 (Sucesso sem conteúdo)
    }

    // Remove um cliente do sistema, verificando previamente sua existência.
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id)
    {
        Cliente clienteSalvo = clienteService.buscar_por_id(id);

        if(clienteSalvo == null)
        {
            return ResponseEntity.notFound().build();
        }

        clienteService.excluir(clienteSalvo);
        return ResponseEntity.noContent().build();
    }

    // Gera um relatório de clientes filtrados por um intervalo de datas
    @GetMapping("/relatorio")
    public List<Cliente> relatorio(@RequestBody RelatorioDTO relatorioDTO)
    {
        // Converte as datas recebidas para LocalDate considerando o fuso horário do sistema
        LocalDate data_inicio = relatorioDTO.data_inicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate data_fim = relatorioDTO.data_final().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return clienteService.gerar_relatorio(data_inicio, data_fim);
    }

}