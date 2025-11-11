package SCOS.springsecurity.Controller;

import SCOS.springsecurity.dto.ClienteDTO;
import SCOS.springsecurity.model.Cliente;
import SCOS.springsecurity.reporitory.ClienteRepository;
import SCOS.springsecurity.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
