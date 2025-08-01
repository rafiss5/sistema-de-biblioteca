package biblioteca.controller;

import biblioteca.dao.UsuarioDAO;
import biblioteca.model.Usuario;
import java.util.List;

public class UsuarioController {
    private final UsuarioDAO usuarioDAO;
    private String senha;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean cadastrarUsuario(Usuario usuario) {
        if (usuarioDAO.buscarPorId(usuario.getMatricula()) != null) {
            return false;
        }
        usuarioDAO.salvar(usuario);
        return true;
    }

    public Usuario buscarUsuario(String matricula) {
        return usuarioDAO.buscarPorId(matricula);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listarTodos();
    }

    public boolean removerUsuario(String matricula) {
        return usuarioDAO.removerComRetorno(matricula);
    }



    public boolean atualizarUsuario(Usuario usuario) {
        if (usuarioDAO.buscarPorId(usuario.getMatricula()) == null) {
            return false;
        }
        usuarioDAO.salvar(usuario);
        return true;
    }
}