package rg_oficina_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rg_oficina_backend.dto.DashboardDto;
import rg_oficina_backend.repository.ClienteRepository;
import rg_oficina_backend.repository.OSRepository;

// Controlador responsável por fornecer métricas e indicadores para a tela inicial (Dashboard)
@RestController
@RequestMapping("/dashboard")
public class DashBoardController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private OSRepository osRepository;

    // Endpoint que consolida os totais de registros do sistema (Clientes e OS) em uma única resposta
    @GetMapping("/dados")
    public ResponseEntity<DashboardDto> obterDadosDashboard(){

        // Utiliza o método .count() do JPA para realizar uma contagem otimizada diretamente no banco
        long totalClientes = clienteRepository.count();
        long totalOS = osRepository.count();

        // Encapsula os dados em um DTO para facilitar o consumo pelo Front-end
        DashboardDto dados = new DashboardDto(totalClientes, totalOS);

        return ResponseEntity.ok(dados);
    }

}