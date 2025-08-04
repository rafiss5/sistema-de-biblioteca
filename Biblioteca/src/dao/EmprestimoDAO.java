package dao;

import model.Emprestimo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EmprestimoDAO {
    private static final String DIRETORIO = "dados";
    private static final String ARQUIVO = DIRETORIO + "/emprestimos.json";
    private List<Emprestimo> emprestimos;
    private final Gson gson;

    public EmprestimoDAO() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new SerializadorLocalDate())
                .registerTypeAdapter(LocalDate.class, new DeserializadorLocalDate())
                .serializeNulls()
                .create();
        criarDiretorioSeNecessario();
        this.emprestimos = carregarDados();
}

    private void criarDiretorioSeNecessario() {
        File dir = new File(DIRETORIO);
        if (!dir.exists() && !dir.mkdirs()) {
            System.err.println("Falha ao criar diretório de dados");
        }
    }

    private List<Emprestimo> carregarDados() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists() || arquivo.length() == 0) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(ARQUIVO)) {
            Type tipoLista = new TypeToken<ArrayList<Emprestimo>>(){}.getType();
            List<Emprestimo> dados = gson.fromJson(reader, tipoLista);
            return dados != null ? dados : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Erro ao carregar empréstimos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void salvarDados() {
        try (FileWriter writer = new FileWriter(ARQUIVO)) {
            gson.toJson(emprestimos, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar empréstimos: " + e.getMessage());
        }
    }

    public boolean salvar(Emprestimo emprestimo) {
        if (emprestimo == null || emprestimo.getId() == null) {
            return false;
        }

        emprestimos.removeIf(e -> e.getId().equals(emprestimo.getId()));
        emprestimos.add(emprestimo);
        salvarDados();
        return true;
    }

    public Emprestimo buscarPorId(String id) {
        if (id == null) {
            return null;
        }
        return emprestimos.stream()
                .filter(e -> id.equals(e.getId()))
                .findFirst()
                .orElse(null);
    }

    public long countEmprestimosPorObra(String codigoObra) {
        if (codigoObra == null) {
            return 0;
        }

        ObraDAO obraDAO = new ObraDAO();

        return emprestimos.stream()
                .filter(Objects::nonNull)
                .filter(e -> obraDAO.buscarPorId(e.getObraId()) != null)
                .filter(e -> codigoObra.equals(obraDAO.buscarPorId(e.getObraId()).getCodigo()))
                .count();
    }

    public List<Emprestimo> listarTodos() {
        return new ArrayList<>(emprestimos);
    }
}