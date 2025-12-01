package rg_oficina_backend.controller;

import rg_oficina_backend.dto.ClienteDTO;
import rg_oficina_backend.dto.OSDTO;
import rg_oficina_backend.dto.RelatorioDTO;
import rg_oficina_backend.entity.Cliente;
import rg_oficina_backend.entity.OS;
import rg_oficina_backend.service.OSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import rg_oficina_backend.service.OSService; // Import duplicado removido para limpeza visual

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/**
 * Controlador de Ordens de Serviço (OSController).
 * Gerencia o fluxo principal da oficina: abertura, edição, consulta e relatórios de serviços.
 */
@RestController
@RequestMapping("/os")
public class OSController
{

    @Autowired
    OSService osService;

    // Retorna a lista de todas as Ordens de Serviço para a grid principal
    @GetMapping("/listar")
    public List<OS> listar()
    {
        return osService.listar_todos();
    }

    // Busca os detalhes de uma OS específica pelo número (ID)
    @GetMapping("/buscar_id/{id}")
    public OS buscar_id(@PathVariable Long id)
    {
        return osService.buscar_por_id(id);
    }

    // Abre uma nova Ordem de Serviço recebendo os dados via DTO
    @PostMapping("/salvar")
    public void salvar(@RequestBody OSDTO osDTO)
    {
        // Log de verificação para monitorar o payload recebido no console
        System.out.println("Recebido: " + osDTO);

        osService.salvar(osDTO);
    }

    // Atualiza uma OS existente. Valida a existência antes de processar.
    @PutMapping("/editar/{id}")
    public ResponseEntity<Void> editar(@RequestBody OSDTO osDTO, @PathVariable Long id)
    {
        OS osSalvo = osService.buscar_por_id(id);

        if(osSalvo == null)
        {
            return ResponseEntity.notFound().build();
        }

        osService.editar(osSalvo, osDTO);

        return ResponseEntity.noContent().build();
    }

    // Exclui uma OS do sistema
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id)
    {
        OS osSalvo = osService.buscar_por_id(id);

        if(osSalvo == null)
        {
            return ResponseEntity.notFound().build();
        }

        osService.excluir(osSalvo);

        return ResponseEntity.noContent().build();
    }

    // Gera relatórios de serviços realizados dentro de um período específico
    @GetMapping("/relatorio")
    public List<OS> relatorio(@RequestBody RelatorioDTO relatorioDTO)
    {
        // Converte datas do JSON para LocalDate usando o fuso horário padrão do sistema
        LocalDate data_inicio = relatorioDTO.data_inicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate data_fim = relatorioDTO.data_final().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return osService.gerar_relatorio(data_inicio, data_fim);
    }

}