package SCOS.springsecurity.Controller;

import SCOS.springsecurity.dto.ClienteDTO;
import SCOS.springsecurity.dto.OSDTO;
import SCOS.springsecurity.dto.RelatorioDTO;
import SCOS.springsecurity.model.Cliente;
import SCOS.springsecurity.model.OS;
import SCOS.springsecurity.service.OSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/os")
public class OSController
{

    @Autowired
    OSService osService;

    @GetMapping("/listar")
    public List<OS> listar()
    {

        return osService.listar_todos();

    }

    @GetMapping("/buscar_id/{id}")
    public OS buscar_id(@PathVariable Long id)
    {

        return osService.buscar_por_id(id);

    }

    @PostMapping("/salvar")
    public void salvar(@RequestBody OSDTO osDTO)
    {

        osService.salvar(osDTO);

    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Void> editar(@RequestBody OSDTO osDTO, @PathVariable Long id)
    {

        OS osSalvo = osService.buscar_por_id(id);

        if(osSalvo == null)
        {

            return ResponseEntity.notFound().build();

        }

        osService.editar(osSalvo);

        return ResponseEntity.noContent().build();

    }

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

    @GetMapping("/relatorio")
    public List<OS> relatorio(@RequestBody RelatorioDTO relatorioDTO)
    {

        LocalDate data_inicio = relatorioDTO.data_inicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate data_fim = relatorioDTO.data_final().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return osService.gerar_relatorio(data_inicio, data_fim);

    }

}
