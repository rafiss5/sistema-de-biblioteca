package biblioteca.dao;

import biblioteca.model.Obra;
import biblioteca.model.Livro;
import biblioteca.model.Revista;
import biblioteca.model.Artigo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ObraDAO implements Persistivel<Obra> {
    private static final String DIRETORIO_DADOS = "dados";
    private static final String ARQUIVO_OBRAS = DIRETORIO_DADOS + "/obras.json";
    private List<Obra> obras;
    private final Gson gson;

    public ObraDAO() {
        // Configura o Gson para lidar com a hierarquia de classes
        RuntimeTypeAdapterFactory<Obra> typeAdapter = RuntimeTypeAdapterFactory
                .of(Obra.class, "tipo")
                .registerSubtype(Livro.class, "Livro")
                .registerSubtype(Revista.class, "Revista")
                .registerSubtype(Artigo.class, "Artigo");

        this.gson = new GsonBuilder()
                .registerTypeAdapterFactory(typeAdapter)
                .setPrettyPrinting()
                .create();

        criarDiretorioSeNaoExistir();
        this.obras = carregarDados();
    }

    private void criarDiretorioSeNaoExistir() {
        File diretorio = new File(DIRETORIO_DADOS);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
    }

    private List<Obra> carregarDados() {
        File arquivo = new File(ARQUIVO_OBRAS);
        if (!arquivo.exists()) {
            System.out.println("Arquivo de obras não encontrado. Criando nova lista.");
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(arquivo)) {
            Type tipoLista = new TypeToken<ArrayList<Obra>>(){}.getType();
            List<Obra> listaCarregada = gson.fromJson(reader, tipoLista);

            System.out.println("Obras carregadas: " + (listaCarregada != null ? listaCarregada.size() : 0));

            return listaCarregada != null ? listaCarregada : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Erro ao carregar obras: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void salvarDados() {
        try (Writer writer = new FileWriter(ARQUIVO_OBRAS)) {
            gson.toJson(obras, writer);
            System.out.println("Obras salvas com sucesso. Total: " + obras.size());
        } catch (Exception e) {
            System.err.println("Erro ao salvar obras: " + e.getMessage());
        }
    }

    @Override
    public boolean salvar(Obra obra) {
        try {
            obras.removeIf(o -> o.getCodigo().equals(obra.getCodigo()));
            obras.add(obra);
            salvarDados();
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao salvar obra: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Obra buscarPorId(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return null;
        }
        return obras.stream()
                .filter(o -> o.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Obra> listarTodos() {
        return new ArrayList<>(obras);
    }

    @Override
    public boolean remover(String codigo) {
        try {
            boolean removido = obras.removeIf(o -> o.getCodigo().equals(codigo));
            if (removido) {
                salvarDados();
            }
            return removido;
        } catch (Exception e) {
            System.err.println("Erro ao remover obra: " + e.getMessage());
            return false;
        }
    }

    public boolean obraExiste(String codigo) {
        return obras.stream().anyMatch(o -> o.getCodigo().equals(codigo));
    }
}