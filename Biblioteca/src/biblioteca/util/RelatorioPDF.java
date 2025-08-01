package biblioteca.util;

import biblioteca.model.Emprestimo;
import biblioteca.model.Obra;
import biblioteca.model.Usuario;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RelatorioPDF {
    private static final Font FONTE_TITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
    private static final Font FONTE_CABECALHO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
    private static final Font FONTE_NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 10);

    public static void gerarRelatorioEmprestimos(List<Emprestimo> emprestimos, String caminhoArquivo)
            throws IOException, DocumentException {

        Document documento = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(documento, new FileOutputStream(caminhoArquivo));
        documento.open();

        // Título
        Paragraph titulo = new Paragraph("Relatório de Empréstimos", FONTE_TITULO);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20f);
        documento.add(titulo);

        // Tabela
        PdfPTable tabela = new PdfPTable(6);
        tabela.setWidthPercentage(100);

        // Cabeçalhos
        adicionarCelula(tabela, "ID Empréstimo", FONTE_CABECALHO);
        adicionarCelula(tabela, "Obra", FONTE_CABECALHO);
        adicionarCelula(tabela, "Usuário", FONTE_CABECALHO);
        adicionarCelula(tabela, "Data Empréstimo", FONTE_CABECALHO);
        adicionarCelula(tabela, "Data Devolução", FONTE_CABECALHO);
        adicionarCelula(tabela, "Status", FONTE_CABECALHO);

        // Dados
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Emprestimo emp : emprestimos) {
            adicionarCelula(tabela, emp.getId(), FONTE_NORMAL);
            adicionarCelula(tabela, emp.getObra().getTitulo(), FONTE_NORMAL);
            adicionarCelula(tabela, emp.getUsuario().getNome(), FONTE_NORMAL);
            adicionarCelula(tabela, emp.getDataEmprestimo().format(formatter), FONTE_NORMAL);
            adicionarCelula(tabela, emp.getDataDevolucaoPrevista().format(formatter), FONTE_NORMAL);
            adicionarCelula(tabela, emp.isDevolvido() ? "Devolvido" : "Pendente", FONTE_NORMAL);
        }

        documento.add(tabela);
        documento.close();
    }

    public static void gerarRelatorioObras(List<Obra> obras, String caminhoArquivo)
            throws IOException, DocumentException {

        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, new FileOutputStream(caminhoArquivo));
        documento.open();

        Paragraph titulo = new Paragraph("Obras Mais Emprestadas", FONTE_TITULO);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20f);
        documento.add(titulo);

        PdfPTable tabela = new PdfPTable(4);
        tabela.setWidthPercentage(100);

        adicionarCelula(tabela, "Código", FONTE_CABECALHO);
        adicionarCelula(tabela, "Título", FONTE_CABECALHO);
        adicionarCelula(tabela, "Autor", FONTE_CABECALHO);
        adicionarCelula(tabela, "Tipo", FONTE_CABECALHO);

        for (Obra obra : obras) {
            adicionarCelula(tabela, obra.getCodigo(), FONTE_NORMAL);
            adicionarCelula(tabela, obra.getTitulo(), FONTE_NORMAL);
            adicionarCelula(tabela, obra.getAutor(), FONTE_NORMAL);
            adicionarCelula(tabela, obra.getClass().getSimpleName(), FONTE_NORMAL);
        }

        documento.add(tabela);
        documento.close();
    }

    public static void gerarRelatorioUsuarios(List<Usuario> usuarios, String caminhoArquivo)
            throws IOException, DocumentException {

        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, new FileOutputStream(caminhoArquivo));
        documento.open();

        Paragraph titulo = new Paragraph("Usuários com Mais Atrasos", FONTE_TITULO);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20f);
        documento.add(titulo);

        PdfPTable tabela = new PdfPTable(4);
        tabela.setWidthPercentage(100);

        adicionarCelula(tabela, "Matrícula", FONTE_CABECALHO);
        adicionarCelula(tabela, "Nome", FONTE_CABECALHO);
        adicionarCelula(tabela, "Email", FONTE_CABECALHO);
        adicionarCelula(tabela, "Atrasos", FONTE_CABECALHO);

        for (Usuario usuario : usuarios) {
            adicionarCelula(tabela, usuario.getMatricula(), FONTE_NORMAL);
            adicionarCelula(tabela, usuario.getNome(), FONTE_NORMAL);
            adicionarCelula(tabela, usuario.getEmail(), FONTE_NORMAL);
            adicionarCelula(tabela, String.valueOf(usuario.getAtrasos()), FONTE_NORMAL);
        }

        documento.add(tabela);
        documento.close();
    }

    private static void adicionarCelula(PdfPTable tabela, String texto, Font fonte) {
        PdfPCell celula = new PdfPCell(new Phrase(texto, fonte));
        celula.setPadding(5);
        tabela.addCell(celula);
    }
}