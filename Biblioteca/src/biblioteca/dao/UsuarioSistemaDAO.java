package biblioteca.dao;

import biblioteca.model.UsuarioSistema;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsuarioSistemaDAO {
    private static final String CAMINHO_ARQUIVO = "usuarios_sistema.json";
    private Gson gson = new Gson();
    private List<UsuarioSistema> usuariosSistema;

    public UsuarioSistemaDAO() {
        carregarDados();
    }

    private void carregarDados() {
        try (FileReader reader = new FileReader(CAMINHO_ARQUIVO)) {
            Type listType = new TypeToken<ArrayList<UsuarioSistema>>(){}.getType();
            usuariosSistema = gson.fromJson(reader, listType);
        } catch (Exception e) {
            usuariosSistema = new ArrayList<>();
        }
    }

    public UsuarioSistema autenticar(String login, String senha) {
        return usuariosSistema.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }
}