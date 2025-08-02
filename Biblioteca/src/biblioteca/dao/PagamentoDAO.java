package biblioteca.dao;

import biblioteca.model.PagamentoMulta;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO implements Persistivel<PagamentoMulta> {
    private static final String ARQUIVO = "pagamentos.json";
    private List<PagamentoMulta> pagamentos;
    private Gson gson = new Gson();

    public PagamentoDAO() {
        pagamentos = carregarDados();
    }

    private List<PagamentoMulta> carregarDados() {
        try (FileReader reader = new FileReader(ARQUIVO)) {
            Type type = new TypeToken<ArrayList<PagamentoMulta>>(){}.getType();
            return gson.fromJson(reader, type);
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

    public List<PagamentoMulta> listarPorUsuario(String usuarioId) {
        return pagamentos.stream()
                .filter(p -> p.getUsuario().getMatricula().equals(usuarioId))
                .toList();
    }

}
