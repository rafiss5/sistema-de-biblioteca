package biblioteca.controller;

import biblioteca.dao.UsuarioSistemaDAO;
import biblioteca.model.UsuarioSistema;

public class UsuarioSistemaController {
    private final UsuarioSistemaDAO usuarioSistemaDAO;
    private int tentativasRestantes = 3;
    private long tempoBloqueio = 0;

    public UsuarioSistemaController() {
        this.usuarioSistemaDAO = new UsuarioSistemaDAO();
    }

    public UsuarioSistema autenticar(String login, String senha) {
        // Verifica se está bloqueado temporariamente
        if (System.currentTimeMillis() < tempoBloqueio) {
            return null;
        }

        UsuarioSistema usuario = usuarioSistemaDAO.autenticar(login, senha);

        if (usuario == null) {
            tentativasRestantes--;
            if (tentativasRestantes <= 0) {
                // Bloqueia por 5 minutos após 3 tentativas falhas
                tempoBloqueio = System.currentTimeMillis() + (5 * 60 * 1000);
                tentativasRestantes = 3; // Reseta para próxima tentativa
            }
        } else {
            // Reseta contador se login for bem-sucedido
            tentativasRestantes = 3;
        }

        return usuario;
    }

    public boolean isBloqueado() {
        return System.currentTimeMillis() < tempoBloqueio;
    }

    public long getTempoRestanteBloqueio() {
        return Math.max(0, (tempoBloqueio - System.currentTimeMillis()) / 1000);
    }
}