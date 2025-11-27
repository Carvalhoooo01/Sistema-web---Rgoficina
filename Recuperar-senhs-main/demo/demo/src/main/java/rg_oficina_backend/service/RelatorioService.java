package rg_oficina_backend.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rg_oficina_backend.entity.OS;
import rg_oficina_backend.repository.OSRepository;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private OSRepository osRepository;

    public ByteArrayInputStream gerarRelatorioOS() {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // 1. Título e Estilo
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE);
            Paragraph title = new Paragraph("OFICINA RG", fontTitulo);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph subTitle = new Paragraph("Relatório Geral de Serviços", FontFactory.getFont(FontFactory.HELVETICA, 12));
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);
            document.add(Chunk.NEWLINE);

            // 2. Tabela
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 3, 3, 4, 2});

            // 3. Cabeçalho Colorido
            String[] headers = {"ID", "Cliente", "Máquina", "Descrição", "Prioridade"};
            for (String header : headers) {
                Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
                PdfPCell cell = new PdfPCell(new Phrase(header, fontHeader));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(Color.DARK_GRAY);
                cell.setPadding(6);
                table.addCell(cell);
            }

            // 4. PREENCHIMENTO DOS DADOS (CORRIGIDO)
            List<OS> listaOs = osRepository.findAll();
            Font fontDados = FontFactory.getFont(FontFactory.HELVETICA, 10);

            for (OS os : listaOs) {
                // ID
                table.addCell(new Phrase(String.valueOf(os.getId()), fontDados));

                // CLIENTE (Correção: Acessa o objeto cliente_id e pega o nome)
                String nomeCliente = "Não Informado";
                if (os.getCliente_id() != null) {
                    // ATENÇÃO: Verifique se na sua classe Cliente o método é getNome() ou getRazaoSocial()
                    nomeCliente = os.getCliente_id().getNome();
                }
                table.addCell(new Phrase(nomeCliente, fontDados));

                // MÁQUINA (Correção: Junta Tipo + Marca + Modelo)
                String maquina = (os.getTipo() != null ? os.getTipo() : "") + " " +
                        (os.getMarca() != null ? os.getMarca() : "") + " " +
                        (os.getModelo() != null ? os.getModelo() : "");
                table.addCell(new Phrase(maquina.trim(), fontDados));

                // DESCRIÇÃO
                table.addCell(new Phrase(os.getDescricao() != null ? os.getDescricao() : "", fontDados));

                // PRIORIDADE
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