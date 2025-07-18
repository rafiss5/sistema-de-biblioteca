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
    private static final String ARQUIVO = "obras.json";
    private Gson gson = new Gson();
    private List<Obra> obras;

    public ObraDAO() {
        obras = new ArrayList<>();
        carregarDados();
    }

    private void carregarDados() {
        try (FileReader reader = new FileReader(ARQUIVO)) {
            Type listType = new TypeToken<ArrayList<Obra>>(){}.getType();
            obras = gson.fromJson(reader, listType);
            if (obras == null) {
                obras = new ArrayList<>();
            }
        } catch (Exception e) {
            System.out.println("Arquivo não encontrado ou inválido. Criando nova lista.");
            obras = new ArrayList<>();
        }
    }

    private void salvarDados() {
        try (FileWriter writer = new FileWriter(ARQUIVO)) {
            gson.toJson(obras, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salvar(Obra obra) {
        if (!obras.contains(obra)) {
            obras.add(obra);
        }
        salvarDados();
    }

    @Override
    public Obra buscarPorId(String id) {
        return obras.stream()
                .filter(o -> o.getCodigo().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Obra> listarTodos() {
        return new ArrayList<>(obras);
    }

    @Override
    public void remover(String id) {
        obras.removeIf(o -> o.getCodigo().equals(id));
        salvarDados();
    }
}