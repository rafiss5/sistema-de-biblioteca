package util;

import dao.ObraDAO;
import model.Emprestimo;
import model.Obra;
import model.Usuario;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class RelatorioPDF {
    private static final Font FONTE_TITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
    private static final Font FONTE_CABECALHO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
    private static final Font FONTE_NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 10);
    private static final DateTimeFormatter FORMATADOR_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void gerarRelatorioEmprestimos(List<Emprestimo> emprestimos, String caminhoArquivo)
            throws DocumentException, IOException {
        gerarRelatorioGenerico(emprestimos, caminhoArquivo, "Relatório de Empréstimos",
                new String[]{"ID Empréstimo", "Obra", "Usuário", "Data Empréstimo", "Data Devolução", "Status"},
                (emp, tabela) -> {
                    adicionarCelula(tabela, emp.getId(), FONTE_NORMAL);
                    adicionarCelula(tabela, new ObraDAO().buscarPorId(emp.getObraId()) != null ? new ObraDAO().buscarPorId(emp.getObraId()).getTitulo() : "N/A", FONTE_NORMAL);
                    adicionarCelula(tabela, emp.getUsuario() != null ? emp.getUsuario().getNome() : "N/A", FONTE_NORMAL);
                    adicionarCelula(tabela, emp.getDataEmprestimo() != null
                            ? emp.getDataEmprestimo().format(FORMATADOR_DATA) : "N/A", FONTE_NORMAL);
                    adicionarCelula(tabela, emp.getDataDevolucao() != null
                            ? emp.getDataDevolucao().format(FORMATADOR_DATA) : "Pendente", FONTE_NORMAL);
                    adicionarCelula(tabela, emp.isDevolvido() ? "Devolvido" : "Pendente", FONTE_NORMAL);
                });
    }

    public static void gerarRelatorioObras(List<Obra> obras, String caminhoArquivo)
            throws DocumentException, IOException {
        gerarRelatorioGenerico(obras, caminhoArquivo, "Obras Mais Emprestadas",
                new String[]{"Código", "Título", "Autor", "Tipo"},
                (obra, tabela) -> {
                    adicionarCelula(tabela, obra.getCodigo(), FONTE_NORMAL);
                    adicionarCelula(tabela, obra.getTitulo(), FONTE_NORMAL);
                    adicionarCelula(tabela, obra.getAutor(), FONTE_NORMAL);
                    adicionarCelula(tabela, obra.getClass().getSimpleName(), FONTE_NORMAL);
                });
    }

    public static void gerarRelatorioUsuarios(List<Usuario> usuarios, String caminhoArquivo)
            throws DocumentException, IOException {
        gerarRelatorioGenerico(usuarios, caminhoArquivo, "Usuários com Mais Atrasos",
                new String[]{"Matrícula", "Nome", "Email", "Atrasos"},
                (usuario, tabela) -> {
                    adicionarCelula(tabela, usuario.getMatricula(), FONTE_NORMAL);
                    adicionarCelula(tabela, usuario.getNome(), FONTE_NORMAL);
                    adicionarCelula(tabela, usuario.getEmail(), FONTE_NORMAL);
                    adicionarCelula(tabela, String.valueOf(usuario.getAtrasos()), FONTE_NORMAL);
                });
    }

    private static <T> void gerarRelatorioGenerico(List<T> itens, String caminhoArquivo, String titulo,
                                                   String[] cabecalhos, RelatorioItemHandler<T> handler) throws DocumentException, IOException {

        Objects.requireNonNull(itens, "Lista de itens não pode ser nula");
        Objects.requireNonNull(caminhoArquivo, "Caminho do arquivo não pode ser nulo");

        Document documento = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(documento, new FileOutputStream(caminhoArquivo));
        documento.open();

        // Título
        Paragraph paragrafoTitulo = new Paragraph(titulo, FONTE_TITULO);
        paragrafoTitulo.setAlignment(Element.ALIGN_CENTER);
        paragrafoTitulo.setSpacingAfter(20f);
        documento.add(paragrafoTitulo);

        // Tabela
        PdfPTable tabela = new PdfPTable(cabecalhos.length);
        tabela.setWidthPercentage(100);

        // Cabeçalhos
        for (String cabecalho : cabecalhos) {
            adicionarCelula(tabela, cabecalho, FONTE_CABECALHO);
        }

        // Dados
        for (T item : itens) {
            if (item != null) {
                handler.handleItem(item, tabela);
            }
        }

        documento.add(tabela);
        documento.close();
    }

    private static void adicionarCelula(PdfPTable tabela, String texto, Font fonte) {
        PdfPCell celula = new PdfPCell(new Phrase(texto != null ? texto : "N/A", fonte));
        celula.setPadding(5);
        tabela.addCell(celula);
    }

    @FunctionalInterface
    private interface RelatorioItemHandler<T> {
        void handleItem(T item, PdfPTable tabela);
    }
}