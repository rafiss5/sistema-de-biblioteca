package biblioteca.controller;

import biblioteca.dao.EmprestimoDAO;
import biblioteca.dao.ObraDAO;
import biblioteca.dao.UsuarioDAO;
import biblioteca.model.Emprestimo;
import biblioteca.model.Obra;
import biblioteca.model.Usuario;
import java.time.LocalDate;
import java.util.List;

public class EmprestimoController {
    private EmprestimoDAO emprestimoDAO;
    private ObraDAO obraDAO;
    private UsuarioDAO usuarioDAO;

    public EmprestimoController() {
        this.emprestimoDAO = new EmprestimoDAO();
        this.obraDAO = new ObraDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    public String realizarEmprestimo(String codigoObra, String matriculaUsuario) {
        Obra obra = obraDAO.buscarPorId(codigoObra);
        Usuario usuario = usuarioDAO.buscarPorId(matriculaUsuario);

        if (obra == null) {
            throw new IllegalArgumentException("Obra não encontrada!");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }
        if (obra.isEmprestado()) {
            throw new IllegalStateException("Obra já está emprestada!");
        }
        if (usuario.getAtrasos() > 3) {
            throw new IllegalStateException("Usuário com mais de 3 atrasos não pode realizar empréstimos!");
        }

        Emprestimo emprestimo = new Emprestimo(
                gerarIdEmprestimo(),
                obra,
                usuario,
                LocalDate.now()
        );

        obra.setEmprestado(true);
        obraDAO.salvar(obra);
        emprestimoDAO.salvar(emprestimo);

        return emprestimo.getId();
    }

    public double registrarDevolucao(String idEmprestimo) {
        Emprestimo emprestimo = emprestimoDAO.buscarPorId(idEmprestimo);
        if (emprestimo == null) {
            throw new IllegalArgumentException("Empréstimo não encontrado!");
        }
        if (emprestimo.isDevolvido()) {
            throw new IllegalStateException("Obra já foi devolvida!");
        }

        LocalDate hoje = LocalDate.now();
        emprestimo.registrarDevolucao(hoje);

        Obra obra = emprestimo.getObra();
        obra.setEmprestado(false);
        obraDAO.salvar(obra);

        emprestimoDAO.salvar(emprestimo);

        return emprestimo.getMulta();
    }

    public List<Emprestimo> listarEmprestimosAtivos() {
        return emprestimoDAO.listarEmprestimosAtivos();
    }

    public List<Emprestimo> listarHistoricoCompleto() {
        return emprestimoDAO.listarTodos();
    }

    private String gerarIdEmprestimo() {
        return "EMP-" + System.currentTimeMillis();
    }
}