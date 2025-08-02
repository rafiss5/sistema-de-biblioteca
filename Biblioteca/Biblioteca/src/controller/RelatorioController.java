package controller;

import model.Emprestimo;
import model.Obra;
import model.Usuario;
import dao.EmprestimoDAO;
import dao.ObraDAO;
import dao.UsuarioDAO;
import util.RelatorioPDF;
import com.lowagie.text.DocumentException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class RelatorioController {
    private final EmprestimoDAO emprestimoDAO;
    private final ObraDAO obraDAO;
    private final UsuarioDAO usuarioDAO;

    public RelatorioController() {
        this.emprestimoDAO = new EmprestimoDAO();
        this.obraDAO = new ObraDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    public RelatorioController(EmprestimoDAO emprestimoDAO, ObraDAO obraDAO, UsuarioDAO usuarioDAO) {
        this.emprestimoDAO = emprestimoDAO;
        this.obraDAO = obraDAO;
        this.usuarioDAO = usuarioDAO;
    }

    public void gerarRelatorioEmprestimosMes(int mes, int ano, String caminho) throws IOException, DocumentException {
        List<Emprestimo> emprestimos = emprestimoDAO.listarTodos().stream()
                .filter(e -> e.getDataEmprestimo().getMonthValue() == mes &&
                        e.getDataEmprestimo().getYear() == ano)
                .collect(Collectors.toList());

        String nomeArquivo = String.format("%s/emprestimos_mes_%d_%d.pdf", caminho, mes, ano);
        RelatorioPDF.gerarRelatorioEmprestimos(emprestimos, nomeArquivo);
    }

    public void gerarRelatorioObrasMaisEmprestadas(String caminho) throws IOException, DocumentException {
        List<Obra> obras = obraDAO.listarTodos().stream()
                .filter(obra -> obra != null && obra.getCodigo() != null)
                .sorted((o1, o2) -> Long.compare(
                        emprestimoDAO.countEmprestimosPorObra(o2.getCodigo()),
                        emprestimoDAO.countEmprestimosPorObra(o1.getCodigo())
                ))
                .limit(10)
                .collect(Collectors.toList());

        String nomeArquivo = caminho + "/obras_mais_emprestadas.pdf";
        RelatorioPDF.gerarRelatorioObras(obras, nomeArquivo);
    }

    public void gerarRelatorioUsuariosComMaisAtrasos(String caminho) throws IOException, DocumentException {
        List<Usuario> usuarios = usuarioDAO.listarTodos().stream()
                .sorted((u1, u2) -> Integer.compare(u2.getAtrasos(), u1.getAtrasos()))
                .limit(10)
                .collect(Collectors.toList());

        String nomeArquivo = caminho + "/usuarios_com_atrasos.pdf";
        RelatorioPDF.gerarRelatorioUsuarios(usuarios, nomeArquivo);
    }
}