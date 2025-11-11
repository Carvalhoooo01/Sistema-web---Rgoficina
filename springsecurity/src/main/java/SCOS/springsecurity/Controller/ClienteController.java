package SCOS.springsecurity.Controller;

import SCOS.springsecurity.model.Cliente;
import SCOS.springsecurity.reporitory.ClienteRepository;
import SCOS.springsecurity.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
