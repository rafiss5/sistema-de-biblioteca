package biblioteca.controller;

import biblioteca.dao.UsuarioSistemaDAO;
import biblioteca.model.UsuarioSistema;

public class UsuarioSistemaController {
    private final UsuarioSistemaDAO usuarioSistemaDAO;

    public UsuarioSistemaController() {
        this.usuarioSistemaDAO = new UsuarioSistemaDAO();
    }

    public UsuarioSistema autenticar(String login, String senha) {
        if (login == null || login.isBlank() || senha == null || senha.isBlank()) {
            return null;
        }
        return usuarioSistemaDAO.autenticar(login, senha);
    }
}