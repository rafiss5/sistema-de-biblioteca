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
    private static final String CAMINHO_ARQUIVO = "usuarios_sistema.json";
    private Gson gson = new Gson();
    private List<UsuarioSistema> listaUsuariosSistema;

    public UsuarioSistemaDAO() {
        listaUsuariosSistema = new ArrayList<>();
        carregarDados();

        // Cria o administrador padrão se a lista estiver vazia
        if (listaUsuariosSistema.isEmpty()) {
            UsuarioSistema admin = new UsuarioSistema("admin", "admin123", UsuarioSistema.PerfilUsuario.ADMINISTRADOR);
            listaUsuariosSistema.add(admin);
            salvarDados();
            System.out.println("✅ Usuário admin criado automaticamente.");
        }
    }

    private void carregarDados() {
        try (FileReader leitor = new FileReader(CAMINHO_ARQUIVO)) {
            Type tipoLista = new TypeToken<ArrayList<UsuarioSistema>>() {}.getType();
            listaUsuariosSistema = gson.fromJson(leitor, tipoLista);
        } catch (Exception erro) {
            System.out.println("⚠️ Não encontrei o arquivo de usuários do sistema. Lista nova criada.");
            listaUsuariosSistema = new ArrayList<>();
        }

        if (listaUsuariosSistema == null) {
            listaUsuariosSistema = new ArrayList<>();
        }
    }

    private void salvarDados() {
        try (FileWriter escritor = new FileWriter(CAMINHO_ARQUIVO)) {
            gson.toJson(listaUsuariosSistema, escritor);
        } catch (Exception erro) {
            System.err.println("Erro ao salvar usuários do sistema: " + erro.getMessage());
        }
    }

    public UsuarioSistema autenticar(String login, String senha) {
        return listaUsuariosSistema.stream()
                .filter(u -> u.getLogin().equals(login) && u.verificarSenha(senha))
                .findFirst()
                .orElse(null);
    }

    public void adicionarUsuario(UsuarioSistema usuario) {
        listaUsuariosSistema.add(usuario);
        salvarDados();
    }
}
