package rg_oficina_backend.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rg_oficina_backend.dto.RelatorioDTO;
import rg_oficina_backend.entity.Cliente;
import rg_oficina_backend.entity.OS;
import rg_oficina_backend.repository.ClienteRepository;
import rg_oficina_backend.repository.OSRepository;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Service de Relatórios (Geração de PDF).
 * Utiliza a biblioteca iText/OpenPDF para desenhar documentos.
 * @author Gustavo Carvalho
 */
@Service
public class RelatorioService {

    @Autowired
    private OSRepository osRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // ============================================================================================
    // RELATÓRIO 1: ORDENS DE SERVIÇO
    // ============================================================================================
    public ByteArrayInputStream gerarRelatorioOS(RelatorioDTO filtros) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            // Conecta o Documento ao Fluxo de Saída (Output Stream)
            PdfWriter.getInstance(document, out);
            document.open();

            // 1. Busca de Dados (Inteligente: Com ou Sem Filtro)
            List<OS> listaOs;
            String textoPeriodo = "Período: Completo";

            if (filtros != null && filtros.data_inicio() != null && filtros.data_final() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String inicioStr = sdf.format(filtros.data_inicio());
                String fimStr = sdf.format(filtros.data_final());

                // Busca filtrada no banco
                listaOs = osRepository.findAllForRelatorio(inicioStr, fimStr);
                textoPeriodo = "Período: " + inicioStr + " até " + fimStr;
            } else {
                listaOs = osRepository.findAll();
            }

            // 2. Montagem Visual (Cabeçalho e Título)
            adicionarCabecalho(document, "Relatório de Ordens de Serviço");
            adicionarPeriodo(document, textoPeriodo);

            // 3. Tabela Dinâmica
            PdfPTable table = new PdfPTable(5); // 5 Colunas
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 3, 3, 4, 2}); // Proporção das larguras

            String[] headers = {"ID", "Cliente", "Máquina", "Descrição", "Prioridade"};
            adicionarCabecalhoTabela(table, headers);

            Font fontDados = FontFactory.getFont(FontFactory.HELVETICA, 10);

            // Preenchimento das linhas
            for (OS os : listaOs) {
                table.addCell(new Phrase(String.valueOf(os.getId()), fontDados));

                // Tratamento para evitar NullPointerException se cliente for nulo
                String nomeCliente = (os.getCliente_id() != null) ? os.getCliente_id().getNome() : "---";
                table.addCell(new Phrase(nomeCliente, fontDados));

                String maquina = (os.getTipo() != null ? os.getTipo() : "") + " " + (os.getMarca() != null ? os.getMarca() : "");
                table.addCell(new Phrase(maquina.trim(), fontDados));

                table.addCell(new Phrase(os.getDescricao() != null ? os.getDescricao() : "", fontDados));
                table.addCell(new Phrase(os.getPrioridade() != null ? os.getPrioridade() : "", fontDados));
            }

            document.add(table);
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        // Retorna o arquivo binário pronto para download
        return new ByteArrayInputStream(out.toByteArray());
    }

    // ============================================================================================
    // RELATÓRIO 2: CLIENTES
    // ============================================================================================
    public ByteArrayInputStream gerarRelatorioClientes(RelatorioDTO filtros) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Lógica de busca similar à de OS
            List<Cliente> clientes;
            String textoPeriodo = "Listagem Completa de Clientes";

            if (filtros != null && filtros.data_inicio() != null && filtros.data_final() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String inicioStr = sdf.format(filtros.data_inicio());
                String fimStr = sdf.format(filtros.data_final());
                clientes = clienteRepository.findAllForRelatorio(inicioStr, fimStr);
                textoPeriodo = "Cadastrados entre: " + inicioStr + " e " + fimStr;
            } else {
                clientes = clienteRepository.findAll();
            }

            adicionarCabecalho(document, "Relatório de Clientes");
            adicionarPeriodo(document, textoPeriodo);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 3, 3, 2, 4}); // Última coluna maior para o endereço

            String[] headers = {"ID", "Nome", "Email", "CPF/CNPJ", "Endereço"};
            adicionarCabecalhoTabela(table, headers);

            Font fontDados = FontFactory.getFont(FontFactory.HELVETICA, 9);

            for (Cliente c : clientes) {
                table.addCell(new Phrase(String.valueOf(c.getId()), fontDados));
                table.addCell(new Phrase(c.getNome() != null ? c.getNome() : "", fontDados));
                table.addCell(new Phrase(c.getEmail() != null ? c.getEmail() : "-", fontDados));
                table.addCell(new Phrase(c.getCpf_cnpj() != null ? c.getCpf_cnpj() : "-", fontDados));

                // Formatação do endereço completo
                String rua = c.getRua_numero() != null ? c.getRua_numero() : "";
                String bairro = c.getBairro() != null ? c.getBairro() : "";
                String enderecoCompleto = rua + (!bairro.isEmpty() ? " - " + bairro : "");
                if (enderecoCompleto.isEmpty()) enderecoCompleto = "-";

                table.addCell(new Phrase(enderecoCompleto, fontDados));
            }

            document.add(table);
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    // --- MÉTODOS AUXILIARES PARA ESTILIZAÇÃO (REUSO DE CÓDIGO) ---

    private void adicionarCabecalho(Document document, String tituloRelatorio) throws DocumentException {
        Font fontEmpresa = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE);
        Paragraph pEmpresa = new Paragraph("OFICINA RG", fontEmpresa);
        pEmpresa.setAlignment(Element.ALIGN_CENTER);
        document.add(pEmpresa);

        Paragraph pTitulo = new Paragraph(tituloRelatorio, FontFactory.getFont(FontFactory.HELVETICA, 12));
        pTitulo.setAlignment(Element.ALIGN_CENTER);
        document.add(pTitulo);
    }

    private void adicionarPeriodo(Document document, String texto) throws DocumentException {
        Paragraph pPeriodo = new Paragraph(texto, FontFactory.getFont(FontFactory.HELVETICA, 10, Color.RED));
        pPeriodo.setAlignment(Element.ALIGN_CENTER);
        pPeriodo.setSpacingAfter(15);
        document.add(pPeriodo);
    }

    private void adicionarCabecalhoTabela(PdfPTable table, String[] headers) {
        for (String h : headers) {
            // Estilo: Fundo cinza escuro, letra branca e negrito
            PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.WHITE)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(Color.DARK_GRAY);
            cell.setPadding(5);
            table.addCell(cell);
        }
    }
}