package dao;

import model.Obra;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
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

        this.gson = new GsonBuilder()
                .registerTypeAdapter(Obra.class, new SerializadorObra())
                .registerTypeAdapter(Obra.class, new DeserializadorObra())
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
        try (FileWriter writer = new FileWriter(ARQUIVO_OBRAS)) {
            Type tipo = new TypeToken<List<Obra>>() {}.getType();
            gson.toJson(obras, tipo, writer);

            System.out.println("Obras salvas com sucesso. Total: " + obras.size());
        } catch (Exception e) {
            System.err.println("Erro ao salvar obras: " + e.getMessage());
        }
    }

    public List<Obra> listarTodos() {
        obras = carregarDados();
        return new ArrayList<>(this.obras);
    }

    @Override
    public boolean salvar(Obra obra) {
        obras = carregarDados();
        try {
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
        obras = carregarDados();
        if (codigo == null || codigo.trim().isEmpty()) {
            return null;
        }

        return obras.stream()
                .filter(o -> o.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean remover(String codigo) {
        obras = carregarDados();
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

}