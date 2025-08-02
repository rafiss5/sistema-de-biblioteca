package dao;

import model.UsuarioSistema;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsuarioSistemaDAO {
    private static final String CAMINHO_ARQUIVO = "dados/usuarios_sistema.json";
    private final Gson gson = new Gson();
    private List<UsuarioSistema> usuariosSistema;

    public UsuarioSistemaDAO() {
        this.usuariosSistema = carregarUsuarios();
    }

    private List<UsuarioSistema> carregarUsuarios() {
        try (FileReader reader = new FileReader(CAMINHO_ARQUIVO)) {
            Type listType = new TypeToken<ArrayList<UsuarioSistema>>(){}.getType();
            List<UsuarioSistema> usuarios = gson.fromJson(reader, listType);
            return usuarios != null ? usuarios : new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public UsuarioSistema autenticar(String login, String senha) {
        return usuariosSistema.stream()
                .filter(u -> u.getLogin().equals(login) && u.getSenha().equals(senha))
                .findFirst()
                .orElse(null);
    }

    // Método auxiliar para verificar se usuário existe
    public boolean existeUsuario(String login) {
        return usuariosSistema.stream()
                .anyMatch(u -> u.getLogin().equals(login));
    }
}