package biblioteca.controller;

import biblioteca.dao.ObraDAO;
import biblioteca.dao.UsuarioDAO;
import biblioteca.model.Obra;
import biblioteca.model.Usuario;
import java.util.List;

public class BibliotecaController {
    private ObraDAO obraDAO;
    private UsuarioDAO usuarioDAO;

    public BibliotecaController() {
        this.obraDAO = new ObraDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    // Métodos para Obras
    public void cadastrarObra(Obra obra) {
        obraDAO.salvar(obra);
    }

    public Obra buscarObra(String codigo) {
        return obraDAO.buscarPorId(codigo);
    }

    public List<Obra> listarObras() {
        return obraDAO.listarTodos();
    }

    public void removerObra(String codigo) {
        obraDAO.remover(codigo);
    }

    // Métodos para Usuários
    public void cadastrarUsuario(Usuario usuario) {
        usuarioDAO.salvar(usuario);
    }

    public Usuario buscarUsuario(String matricula) {
        return usuarioDAO.buscarPorId(matricula);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listarTodos();
    }

    public void removerUsuario(String matricula) {
        usuarioDAO.remover(matricula);
    }
}