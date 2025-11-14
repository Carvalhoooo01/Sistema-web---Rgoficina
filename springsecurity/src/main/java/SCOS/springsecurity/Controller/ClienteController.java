package SCOS.springsecurity.Controller;

import SCOS.springsecurity.dto.ClienteDTO;
import SCOS.springsecurity.dto.RelatorioDTO;
import SCOS.springsecurity.model.Cliente;
import SCOS.springsecurity.model.OS;
import SCOS.springsecurity.reporitory.ClienteRepository;
import SCOS.springsecurity.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController
{

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/buscar_id/{id}")
    public Cliente findById(@PathVariable Long id)
    {

        return clienteService.buscar_por_id(id);

    }

    @GetMapping("/listar")
    public List<Cliente> listar_todos()
    {

        return clienteService.listar_todos();

    }

    @PostMapping("/salvar")
    public void salvar(@RequestBody ClienteDTO clienteDTO)
    {

        clienteService.salvar(clienteDTO);

    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Void> editar(@RequestBody ClienteDTO clienteDTO, @PathVariable Long id)
    {

        Cliente clienteSalvo = clienteService.buscar_por_id(id);

        if(clienteSalvo == null)
        {

            return ResponseEntity.notFound().build();

        }

        clienteService.editar(clienteSalvo);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id)
    {

        Cliente  clienteSalvo = clienteService.buscar_por_id(id);

        if(clienteSalvo == null)
        {

            return ResponseEntity.notFound().build();

        }

        clienteService.excluir(clienteSalvo);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/relatorio")
    public List<Cliente> relatorio(@RequestBody RelatorioDTO relatorioDTO)
    {

        LocalDate data_inicio = relatorioDTO.data_inicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate data_fim = relatorioDTO.data_final().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return clienteService.gerar_relatorio(data_inicio, data_fim);

    }

}
