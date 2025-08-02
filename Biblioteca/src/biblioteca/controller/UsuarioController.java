package biblioteca.controller;

import biblioteca.dao.UsuarioDAO;
import biblioteca.dao.UsuarioSistemaDAO;
import biblioteca.model.Usuario;
import biblioteca.model.UsuarioSistema;

import java.util.List;

public class UsuarioController {
    private final UsuarioDAO usuarioDAO;
    private final UsuarioSistemaDAO usuarioSistemaDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
        this.usuarioSistemaDAO = new UsuarioSistemaDAO();
    }




    // Lógica para usuários da biblioteca (ex: estudantes, visitantes)
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
