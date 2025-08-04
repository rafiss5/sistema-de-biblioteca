package dao;

import com.google.gson.GsonBuilder;
import model.Obra;
import model.PagamentoMulta;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO implements Persistivel<PagamentoMulta> {
    private static final String ARQUIVO = "dados/pagamentos.json";
    private List<PagamentoMulta> pagamentos;
    private final Gson gson;

    public PagamentoDAO() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new SerializadorLocalDate())
                .registerTypeAdapter(LocalDate.class, new DeserializadorLocalDate())
                .create();
        pagamentos = new ArrayList<>();
    }


    private List<PagamentoMulta> carregarDados() {

        try (FileReader reader = new FileReader(ARQUIVO)) {
            Type tipoLista = new TypeToken<ArrayList<PagamentoMulta>>(){}.getType();

            List<PagamentoMulta> listaCarregada = gson.fromJson(reader, tipoLista);

            return listaCarregada != null ? listaCarregada : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void salvarDados() {
        try (FileWriter writer = new FileWriter(ARQUIVO)) {
            gson.toJson(pagamentos, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean salvar(PagamentoMulta pagamento) {
        pagamentos = carregarDados();
        pagamentos.add(pagamento);
        salvarDados();
        return false;
    }

    @Override
    public PagamentoMulta buscarPorId(String id) {
        return null;
    }

    @Override
    public List<PagamentoMulta> listarTodos() {
        return List.of();
    }

    @Override
    public boolean remover(String id) {

        return false;
    }

}
