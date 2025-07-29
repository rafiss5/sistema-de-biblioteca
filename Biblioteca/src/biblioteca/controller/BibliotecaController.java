package biblioteca.controller;

import biblioteca.dao.ObraDAO;
import biblioteca.dao.UsuarioDAO;
import biblioteca.model.Obra;
import biblioteca.model.Usuario;
import java.util.List;

public class BibliotecaController {
    // Controllers especializados como campos privados
    private final ObraController obraController;
    private final UsuarioController usuarioController;

    public BibliotecaController() {
        this.obraController = new ObraController();
        this.usuarioController = new UsuarioController();
    }

    // Métodos para Obras (delegação)
    public void cadastrarObra(Obra obra) {
        obraController.cadastrarObra(obra);
    }

    public Obra buscarObra(String codigo) {
        return obraController.buscarObra(codigo);
    }

    public List<Obra> listarObras() {
        return obraController.listarObras();
    }

    public void removerObra(String codigo) {
        obraController.removerObra(codigo);
    }

    // Métodos para Usuários (delegação)
    public boolean cadastrarUsuario(Usuario usuario) {
        return usuarioController.cadastrarUsuario(usuario);
    }

    public boolean atualizarUsuario(Usuario usuario) {
        return usuarioController.atualizarUsuario(usuario);
    }

    public Usuario buscarUsuario(String matricula) {
        return usuarioController.buscarUsuario(matricula);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioController.listarUsuarios();
    }

    public boolean removerUsuario(String matricula) {
        return usuarioController.removerUsuario(matricula);
    }

    // Classes Controller internas (implementação encapsulada)
    private static class ObraController {
        private final ObraDAO obraDAO = new ObraDAO();

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
    }

    private static class UsuarioController {
        private final UsuarioDAO usuarioDAO = new UsuarioDAO();

        public boolean cadastrarUsuario(Usuario usuario) {
            if (usuario == null || usuario.getMatricula() == null) {
                return false;
            }
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
            if (usuarioDAO.buscarPorId(matricula) == null) {
                return false;
            }
            usuarioDAO.remover(matricula);
            return true;
        }
    }
}