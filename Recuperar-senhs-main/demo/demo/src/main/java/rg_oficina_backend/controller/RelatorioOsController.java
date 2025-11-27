package rg_oficina_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rg_oficina_backend.service.RelatorioService;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/relatorios")
public class RelatorioOsController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/geral")
    public ResponseEntity<InputStreamResource> baixarRelatorio() {
        // 1. Chama o serviço que você acabou de criar
        ByteArrayInputStream pdfStream = relatorioService.gerarRelatorioOS();

        // 2. Configura o nome do arquivo que será baixado
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=relatorio_oficina.pdf");

        // 3. Entrega o arquivo para o navegador
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}