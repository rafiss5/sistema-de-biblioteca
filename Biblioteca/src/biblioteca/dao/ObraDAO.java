package biblioteca.dao;

import biblioteca.model.Obra;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ObraDAO implements Persistivel<Obra> {
    private static final String CAMINHO_ARQUIVO = "obras.json";
    private Gson conversorJson = new Gson();
    private List<Obra> listaDeObras;

    public ObraDAO() {
        listaDeObras = new ArrayList<>();
        carregarDados();
    }

    // Lê o arquivo de obras ou cria uma lista vazia caso não exista
    private void carregarDados() {
        try (FileReader leitor = new FileReader(CAMINHO_ARQUIVO)) {
            Type tipoLista = new TypeToken<ArrayList<Obra>>(){}.getType();
            listaDeObras = conversorJson.fromJson(leitor, tipoLista);
        } catch (Exception erro) {
            System.out.println("⚠️ Não encontrei o arquivo de obras. Lista nova criada.");
            listaDeObras = new ArrayList<>();
        }

        if (listaDeObras == null) {
            listaDeObras = new ArrayList<>();
        }
    }

    private void salvarDados() {
        try (FileWriter escritor = new FileWriter(CAMINHO_ARQUIVO)) {
            conversorJson.toJson(listaDeObras, escritor);
        } catch (Exception erro) {
            System.err.println("Erro ao salvar obras: " + erro.getMessage());
        }
    }

    @Override
    public void salvar(Obra obra) {
        if (!listaDeObras.contains(obra)) {
            listaDeObras.add(obra);
        }
        salvarDados();
    }

    @Override
    public Obra buscarPorId(String id) {
        return listaDeObras.stream()
                .filter(o -> o.getCodigo().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Obra> listarTodos() {
        return new ArrayList<>(listaDeObras);
    }

    @Override
    public void remover(String id) {
        listaDeObras.removeIf(o -> o.getCodigo().equals(id));
        salvarDados();
    }
}
