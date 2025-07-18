package biblioteca.dao;

import biblioteca.model.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements Persistivel<Usuario> {
    private static final String ARQUIVO = "usuarios.json";
    private Gson gson = new Gson();
    private List<Usuario> usuarios;

    public UsuarioDAO() {
        usuarios = new ArrayList<>();
        carregarDados();
    }

    private void carregarDados() {
        try (FileReader reader = new FileReader(ARQUIVO)) {
            Type listType = new TypeToken<ArrayList<Usuario>>(){}.getType();
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

    @Override
    public void salvar(Usuario usuario) {
        if (!usuarios.contains(usuario)) {
            usuarios.add(usuario);
        }
        salvarDados();
    }

    @Override
    public Usuario buscarPorId(String id) {
        return usuarios.stream()
                .filter(u -> u.getMatricula().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public void remover(String id) {
        usuarios.removeIf(u -> u.getMatricula().equals(id));
        salvarDados();
    }
}