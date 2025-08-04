package controller;

import dao.*;
import model.*;
import java.time.LocalDate;
import java.util.List;

public class EmprestimoController {
    private final EmprestimoDAO emprestimoDAO;
    private final ObraDAO obraDAO;
    private final UsuarioDAO usuarioDAO;
    private final PagamentoDAO pagamentoDAO;

    public EmprestimoController() {
        this.emprestimoDAO = new EmprestimoDAO();
        this.obraDAO = new ObraDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.pagamentoDAO = new PagamentoDAO();
    }

    public String realizarEmprestimo(String obraId, String usuarioId) {
        try {
            Obra obra = new ObraDAO().buscarPorId(obraId);
            Usuario usuario = usuarioDAO.buscarPorId(usuarioId);

            if (obraId == null || usuario == null || obra.isEmprestado() || usuario.getAtrasos() > 3) {
                return null;
            }

            String emprestimoId = "EMP-" + System.currentTimeMillis();
            Emprestimo emprestimo = new Emprestimo(emprestimoId, obraId, usuario, LocalDate.now());

            obra.setEmprestado(true);
            emprestimoDAO.salvar(emprestimo);

            return emprestimoId;
        } catch (Exception e) {
            System.err.println("Erro ao realizar empréstimo: " + e.getMessage());
            return null;
        }
    }

    public double registrarDevolucao(String emprestimoId) {
        try {
            Emprestimo emprestimo = emprestimoDAO.buscarPorId(emprestimoId);
            if (emprestimo == null || emprestimo.isDevolvido()) {
                return -1;
            }

            emprestimo.registrarDevolucao(LocalDate.now());
            Obra obra = obraDAO.buscarPorId(emprestimo.getObraId());
            obra.setEmprestado(false);

            if (emprestimo.isAtrasado()) {
                emprestimo.getUsuario().incrementarAtrasos();
                usuarioDAO.salvar(emprestimo.getUsuario());
            }

            obraDAO.salvar(obra);
            emprestimoDAO.salvar(emprestimo);

            return emprestimo.getMulta();
        } catch (Exception e) {
            System.err.println("Erro ao registrar devolução: " + e.getMessage());
            return -1;
        }
    }

    public boolean registrarPagamento(String emprestimoId, double valor, String metodo) {
        try {
            Emprestimo emprestimo = emprestimoDAO.buscarPorId(emprestimoId);
            if (emprestimo == null || !emprestimo.isDevolvido() || valor <= 0) {
                return false;
            }

            PagamentoMulta pagamento = new PagamentoMulta(
                    "PAG-" + System.currentTimeMillis(),
                    valor,
                    LocalDate.now(),
                    metodo,
                    emprestimo.getUsuario()
            );

            return pagamentoDAO.salvar(pagamento);
        } catch (Exception e) {
            System.err.println("Erro ao registrar pagamento: " + e.getMessage());
            return false;
        }
    }
}