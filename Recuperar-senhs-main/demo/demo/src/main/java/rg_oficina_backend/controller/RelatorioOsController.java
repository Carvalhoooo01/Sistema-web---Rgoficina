package rg_oficina_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rg_oficina_backend.dto.RelatorioDTO;
import rg_oficina_backend.service.RelatorioService;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/relatorios")
public class RelatorioOsController {

    @Autowired
    private RelatorioService relatorioService;

    // Mudamos para POST pois estamos enviando dados (datas) no corpo da requisição
    @PostMapping("/gerar")
    public ResponseEntity<InputStreamResource> baixarRelatorio(@RequestBody(required = false) RelatorioDTO filtros) {

        // Passa o DTO (que pode vir com datas ou nulo) para o serviço
        ByteArrayInputStream pdfStream = relatorioService.gerarRelatorioOS(filtros);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=relatorio_oficina.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}