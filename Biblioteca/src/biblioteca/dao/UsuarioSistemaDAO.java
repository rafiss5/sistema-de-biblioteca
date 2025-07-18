package biblioteca.dao;

import biblioteca.model.UsuarioSistema;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsuarioSistemaDAO {
    private static final String ARQUIVO = "usuarios_sistema.json";
    private Gson gson = new Gson();
    private List<UsuarioSistema> usuarios;

    public UsuarioSistemaDAO() {
        usuarios = new ArrayList<>();
        carregarDados();

        // Se não houver usuários, cria um admin padrão
        if (usuarios.isEmpty()) {
            usuarios.add(new UsuarioSistema("admin", "admin123", UsuarioSistema.PerfilUsuario.ADMINISTRADOR));
            salvarDados();
        }
    }

    private void carregarDados() {
        try (FileReader reader = new FileReader(ARQUIVO)) {
            Type listType = new TypeToken<ArrayList<UsuarioSistema>>(){}.getType();
            usuarios = gson.fromJson(reader, listType);
            if (usuarios == null) {
                usuarios = new ArrayList<>();
            }
        } catch (Exception e) {
            System.out.println("Arquivo não encontrado ou inválido. Criando nova lista.");
            usuarios = new ArrayList<>();
        }
    }

    private void salvarDados() {
        try (FileWriter writer = new FileWriter(ARQUIVO)) {
            gson.toJson(usuarios, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UsuarioSistema autenticar(String login, String senha) {
        return usuarios.stream()
                .filter(u -> u.getLogin().equals(login) && u.verificarSenha(senha))
                .findFirst()
                .orElse(null);
    }

    public void adicionarUsuario(UsuarioSistema usuario) {
        usuarios.add(usuario);
        salvarDados();
    }
}