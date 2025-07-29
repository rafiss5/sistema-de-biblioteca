package biblioteca.util;

import biblioteca.model.Obra;
import biblioteca.model.Usuario;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class RelatorioPDF {

    private static final Font FONTE_CABECALHO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
    private static final Font FONTE_NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 10);

    // Relatório de obras
    public static void gerarRelatorioObras(List<Obra> obras, String caminhoArquivo) throws IOException, DocumentException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, new FileOutputStream(caminhoArquivo));
        documento.open();

        PdfPTable tabela = new PdfPTable(5); // Código, Título, Autor, Ano, Status
        tabela.setWidthPercentage(100);

        // Cabeçalhos
        adicionarCelula(tabela, "Código", FONTE_CABECALHO);
        adicionarCelula(tabela, "Título", FONTE_CABECALHO);
        adicionarCelula(tabela, "Autor", FONTE_CABECALHO);
        adicionarCelula(tabela, "Ano", FONTE_CABECALHO);
        adicionarCelula(tabela, "Status", FONTE_CABECALHO);

        // Dados
        for (Obra obra : obras) {
            adicionarCelula(tabela, obra.getCodigo(), FONTE_NORMAL);
            adicionarCelula(tabela, obra.getTitulo(), FONTE_NORMAL);
            adicionarCelula(tabela, obra.getAutor(), FONTE_NORMAL);
            adicionarCelula(tabela, String.valueOf(obra.getAnoPublicacao()), FONTE_NORMAL);
            adicionarCelula(tabela, obra.isEmprestado() ? "Emprestado" : "Disponível", FONTE_NORMAL);
        }

        documento.add(tabela);
        documento.close();
    }

    // Relatório de usuários
    public static void gerarRelatorioUsuarios(List<Usuario> usuarios, String caminhoArquivo) throws IOException, DocumentException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, new FileOutputStream(caminhoArquivo));
        documento.open();

        PdfPTable tabela = new PdfPTable(4); // Nome, Matrícula, Email, Perfil
        tabela.setWidthPercentage(100);

        // Cabeçalhos
        adicionarCelula(tabela, "Nome", FONTE_CABECALHO);
        adicionarCelula(tabela, "Matrícula", FONTE_CABECALHO);
        adicionarCelula(tabela, "Email", FONTE_CABECALHO);
        adicionarCelula(tabela, "Perfil", FONTE_CABECALHO);

        // Dados
        for (Usuario usuario : usuarios) {
            adicionarCelula(tabela, usuario.getNome(), FONTE_NORMAL);
            adicionarCelula(tabela, usuario.getMatricula(), FONTE_NORMAL);
            adicionarCelula(tabela, usuario.getEmail(), FONTE_NORMAL);
        }

        documento.add(tabela);
        documento.close();
    }

    // Método auxiliar para adicionar células
    private static void adicionarCelula(PdfPTable tabela, String texto, Font fonte) {
        PdfPCell celula = new PdfPCell(new Paragraph(texto, fonte));
        tabela.addCell(celula);}
}