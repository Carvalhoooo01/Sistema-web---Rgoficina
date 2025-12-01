package rg_oficina_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rg_oficina_backend.dto.DashboardDto;
import rg_oficina_backend.repository.ClienteRepository;
import rg_oficina_backend.repository.OSRepository;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController {

@Autowired
    private ClienteRepository clienteRepository;

@Autowired
    private OSRepository osRepository;

@GetMapping("/dados")
    public ResponseEntity<DashboardDto> obterDadosDashboard(){
        long totalClientes = clienteRepository.count();
        long totalOS = osRepository.count();

        DashboardDto dados = new DashboardDto(totalClientes, totalOS);
        return ResponseEntity.ok(dados);

}

}
