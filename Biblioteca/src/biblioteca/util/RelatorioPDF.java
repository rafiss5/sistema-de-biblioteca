package biblioteca.util;

import biblioteca.model.Obra;
import biblioteca.model.Usuario;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class RelatorioPDF {

    private static final Font FONT_CABECALHO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
    private static final Font FONT_NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 10);

    public static void gerarRelatorioObras(List<Obra> obras, String caminhoArquivo) throws IOException, DocumentException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, new FileOutputStream(caminhoArquivo));
        documento.open();

        PdfPTable tabela = new PdfPTable(5); // 5 colunas
        tabela.setWidthPercentage(100);

        // Cabeçalho
        adicionarCelula(tabela, "Código", FONT_CABECALHO);
        adicionarCelula(tabela, "Título", FONT_CABECALHO);
        adicionarCelula(tabela, "Autor", FONT_CABECALHO);
        adicionarCelula(tabela, "Ano", FONT_CABECALHO);
        adicionarCelula(tabela, "Status", FONT_CABECALHO);

        // Dados
        for (Obra obra : obras) {
            adicionarCelula(tabela, obra.getCodigo(), FONT_NORMAL);
            adicionarCelula(tabela, obra.getTitulo(), FONT_NORMAL);
            adicionarCelula(tabela, obra.getAutor(), FONT_NORMAL);
            adicionarCelula(tabela, String.valueOf(obra.getAnoPublicacao()), FONT_NORMAL);
            adicionarCelula(tabela, obra.isEmprestado() ? "Emprestado" : "Disponível", FONT_NORMAL);
        }

        documento.add(tabela);
        documento.close();
    }

    private static void adicionarCelula(PdfPTable tabela, String texto, Font fonte) {
        PdfPCell celula = new PdfPCell(new Paragraph(texto, fonte));
        tabela.addCell(celula);
    }

    public static void gerarRelatorioUsuarios(List<Usuario> usuarios, String caminho) {
    }
}
