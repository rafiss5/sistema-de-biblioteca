package biblioteca.controller;

import biblioteca.dao.ObraDAO;
import biblioteca.dao.UsuarioDAO;
import biblioteca.model.Obra;
import biblioteca.model.Usuario;
import java.util.List;

public class BibliotecaController {
    private final ObraDAO obraDAO;
    private final UsuarioDAO usuarioDAO;

    public BibliotecaController() {
        this.obraDAO = new ObraDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    // Métodos para Obras (mantidos inalterados)
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

    // Métodos para Usuários (atualizados)
    public boolean cadastrarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getMatricula() == null) {
            return false;
        }

        // Verifica se usuário já existe
        if (usuarioDAO.buscarPorId(usuario.getMatricula()) != null) {
            return false;
        }

        usuarioDAO.salvar(usuario);
        return true;
    }

    public boolean atualizarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getMatricula() == null) {
            return false;
        }

        // Verifica se usuário existe antes de atualizar
        if (usuarioDAO.buscarPorId(usuario.getMatricula()) == null) {
            return false;
        }

        usuarioDAO.salvar(usuario);
        return true;
    }

    public Usuario buscarUsuario(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            return null;
        }
        return usuarioDAO.buscarPorId(matricula);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listarTodos();
    }

    public boolean removerUsuario(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            return false;
        }

        Usuario usuario = usuarioDAO.buscarPorId(matricula);
        if (usuario == null) {
            return false;
        }

        usuarioDAO.remover(matricula);
        return true;
    }
}