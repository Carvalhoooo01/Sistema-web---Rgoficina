package rg_oficina_backend.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rg_oficina_backend.dto.RelatorioDTO;
import rg_oficina_backend.entity.OS;
import rg_oficina_backend.repository.OSRepository;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private OSRepository osRepository;

    public ByteArrayInputStream gerarRelatorioOS(RelatorioDTO filtros) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // --- 1. LÓGICA DE FILTRO DE DATAS ---
            List<OS> listaOs;
            String textoPeriodo = "Período: Completo (Todas as OS)";

            if (filtros != null && filtros.data_inicio() != null && filtros.data_final() != null) {
                // Formata a data do Java para String (dd/MM/yyyy) que o banco espera
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String inicioStr = sdf.format(filtros.data_inicio());
                String fimStr = sdf.format(filtros.data_final());

                // Busca filtrada
                listaOs = osRepository.findAllForRelatorio(inicioStr, fimStr);
                textoPeriodo = "Período: " + inicioStr + " até " + fimStr;
            } else {
                // Busca tudo se não mandar data
                listaOs = osRepository.findAll();
            }
            // ------------------------------------

            // 2. CABEÇALHO DO PDF
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE);
            Paragraph title = new Paragraph("OFICINA RG", fontTitulo);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph subTitle = new Paragraph("Relatório Geral de Serviços", FontFactory.getFont(FontFactory.HELVETICA, 12));
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);

            Paragraph periodo = new Paragraph(textoPeriodo, FontFactory.getFont(FontFactory.HELVETICA, 10, Color.RED));
            periodo.setAlignment(Element.ALIGN_CENTER);
            periodo.setSpacingAfter(20); // Espaço antes da tabela
            document.add(periodo);

            // 3. TABELA
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 3, 4, 4, 2}); // Ajustei larguras

            // Cabeçalho da Tabela
            String[] headers = {"ID", "Cliente", "Máquina", "Descrição", "prioridade"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.WHITE)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(Color.DARK_GRAY);
                cell.setPadding(5);
                table.addCell(cell);
            }

            // 4. PREENCHIMENTO DOS DADOS
            Font fontDados = FontFactory.getFont(FontFactory.HELVETICA, 10);

            for (OS os : listaOs) {
                // ID
                table.addCell(new Phrase(String.valueOf(os.getId()), fontDados));

                // Cliente (Tratamento de erro se for nulo)
                String nomeCliente = (os.getCliente_id() != null) ? os.getCliente_id().getNome() : "Sem Cliente";
                table.addCell(new Phrase(nomeCliente, fontDados));

                // Máquina (Concatenação segura)
                String maquina = (os.getTipo() != null ? os.getTipo() : "") + " " +
                        (os.getMarca() != null ? os.getMarca() : "");
                table.addCell(new Phrase(maquina.trim(), fontDados));

                // Descrição
                table.addCell(new Phrase(os.getDescricao() != null ? os.getDescricao() : "", fontDados));

                // Prioridade
                table.addCell(new Phrase(os.getPrioridade() != null ? os.getPrioridade() : "-", fontDados));
            }

            document.add(table);
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}