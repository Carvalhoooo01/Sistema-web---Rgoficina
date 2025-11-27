package rg_oficina_backend.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rg_oficina_backend.model.OrdemServico;
import rg_oficina_backend.repository.OsRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private OsRepository osRepository;

    public ByteArrayInputStream gerarRelatorioOS() {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // 1. Título
            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Paragraph title = new Paragraph("Relatório Geral - Oficina RG", fontHeader);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // 2. Tabela com 5 Colunas
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            // Ajuste de largura: ID pequeno, Descrição grande
            table.setWidths(new int[]{1, 3, 3, 4, 2});

            // 3. Cabeçalho
            String[] headers = {"ID", "Cliente", "Máquina", "Descrição", "Prioridade"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            // 4. Buscar TODOS os dados (sem filtro de status)
            List<OrdemServico> listaOs = osRepository.findAll();

            // 5. Preencher linhas
            for (OrdemServico os : listaOs) {
                table.addCell(os.getId().toString());
                table.addCell(os.getCliente());
                table.addCell(os.getMaquina());
                table.addCell(os.getDescricao());
                table.addCell(os.getPrioridade());
            }

            document.add(table);
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}