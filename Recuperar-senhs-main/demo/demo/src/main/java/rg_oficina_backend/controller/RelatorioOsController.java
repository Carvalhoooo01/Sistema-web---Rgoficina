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

/**
 * Controlador de Relatórios.
 * Responsável por gerar e entregar arquivos PDF via streaming de dados.
 */
@RestController
@RequestMapping("/relatorio")
public class RelatorioOsController {

    @Autowired
    private RelatorioService relatorioService;

    // --- RELATÓRIO 1: ORDENS DE SERVIÇO (POR DATA) ---
    // Endpoint para gerar o PDF de OS. Usa POST para receber o objeto de filtros no Body.
    @PostMapping("/ordemservico")
    public ResponseEntity<InputStreamResource> baixarRelatorio(@RequestBody(required = false) RelatorioDTO filtros) {

        // Chama o serviço que constrói o PDF e retorna os bytes em memória
        ByteArrayInputStream pdfStream = relatorioService.gerarRelatorioOS(filtros);

        System.out.println("Gerando relatório de OS com filtros: " + filtros);

        // Configura o cabeçalho para o navegador entender como tratar o arquivo
        HttpHeaders headers = new HttpHeaders();
        // "inline" faz o PDF abrir no navegador. Se fosse "attachment", faria o download direto.
        headers.add("Content-Disposition", "inline; filename=relatorio_oficina.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF) // Define o tipo de arquivo MIME
                .body(new InputStreamResource(pdfStream)); // Envia o fluxo de dados do arquivo
    }

    // --- RELATÓRIO 2: CLIENTES (LISTA GERAL) ---
    @PostMapping("/cliente")
    public ResponseEntity<InputStreamResource> baixarRelatorioClientes(@RequestBody(required = false) RelatorioDTO filtros) {

        // Passa os filtros de data para o serviço de clientes
        ByteArrayInputStream pdfStream = relatorioService.gerarRelatorioClientes(filtros);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=relatorio_clientes.pdf");

        System.out.println("Gerando relatório de Clientes com filtros: " + filtros);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}