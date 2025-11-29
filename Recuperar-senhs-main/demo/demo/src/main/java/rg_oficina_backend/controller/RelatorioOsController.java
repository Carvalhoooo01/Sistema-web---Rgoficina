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
@RequestMapping("/relatorio")
public class RelatorioOsController {

    @Autowired
    private RelatorioService relatorioService;

    // --- RELATÓRIO 1: ORDENS DE SERVIÇO (POR DATA) ---
    @PostMapping("/OrdemServico")
    public ResponseEntity<InputStreamResource> baixarRelatorio(@RequestBody(required = false) RelatorioDTO filtros) {

        ByteArrayInputStream pdfStream = relatorioService.gerarRelatorioOS(filtros);

        System.out.println(filtros);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=relatorio_oficina.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }

    // --- RELATÓRIO 2: CLIENTES (LISTA GERAL) ---
    // Mudamos para POST para aceitar o JSON com as datas
    @PostMapping("/cliente")
    public ResponseEntity<InputStreamResource> baixarRelatorioClientes(@RequestBody(required = false) RelatorioDTO filtros) {

        // Agora passamos os 'filtros' (datas) para o serviço em vez de null
        ByteArrayInputStream pdfStream = relatorioService.gerarRelatorioClientes(filtros);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=relatorio_clientes.pdf");

        System.out.println(filtros);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}