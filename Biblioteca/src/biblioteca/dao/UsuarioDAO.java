package biblioteca.dao;

import biblioteca.model.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements Persistivel<Usuario> {
    private static final String CAMINHO_PASTA = "Dados dos Usuários";
    private static final String CAMINHO_ARQUIVO = CAMINHO_PASTA + "/usuarios.json";
    private Gson gson = new Gson();
    private List<Usuario> listaDeUsuarios;

    public UsuarioDAO() {
        criarPastaSeNaoExistir();
        listaDeUsuarios = new ArrayList<>();
        carregarDados();
    }

    private void criarPastaSeNaoExistir() {
        File pasta = new File(CAMINHO_PASTA);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
    }

    private void carregarDados() {
        try (FileReader leitor = new FileReader(CAMINHO_ARQUIVO)) {
            Type tipoLista = new TypeToken<ArrayList<Usuario>>() {
            }.getType();
            listaDeUsuarios = gson.fromJson(leitor, tipoLista);
        } catch (Exception erro) {
            System.out.println("⚠ Arquivo de usuários não encontrado. Criando lista nova.");
            listaDeUsuarios = new ArrayList<>();
        }

        if (listaDeUsuarios == null) {
            listaDeUsuarios = new ArrayList<>();
        }
    }

    private void salvarDados() {
        try (FileWriter escritor = new FileWriter(CAMINHO_ARQUIVO)) {
            gson.toJson(listaDeUsuarios, escritor);
        } catch (Exception erro) {
            System.err.println("Erro ao salvar usuários: " + erro.getMessage());
        }
    }

    @Override
    public void salvar(Usuario usuario) {
        // Primeiro remove o usuário existente se já houver
        remover(usuario.getMatricula());

        // Depois adiciona o novo usuário (ou atualizado)
        listaDeUsuarios.add(usuario);

        // Por fim persiste os dados
        salvarDados();
    }

    @Override
    public Usuario buscarPorId(String id) {
        return listaDeUsuarios.stream().filter(u -> u.getMatricula().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(listaDeUsuarios);
    }

    @Override
    public void remover(String id) {
        listaDeUsuarios.removeIf(u -> u.getMatricula().equals(id));
        salvarDados();
    }
    
    public void debugListaUsuarios() {
        System.out.println("=== USUÁRIOS CADASTRADOS ===");
        listaDeUsuarios.forEach(u -> System.out.println(u.getMatricula() + " | " + u.getNome() + " | " + u.getEmail()));
        System.out.println("Total: " + listaDeUsuarios.size() + " usuários");
    }
}
