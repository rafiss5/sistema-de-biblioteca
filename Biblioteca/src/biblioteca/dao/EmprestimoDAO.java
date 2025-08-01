package biblioteca.dao;

import biblioteca.model.Emprestimo;
import biblioteca.util.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO implements Persistivel<Emprestimo> {
    private static final String CAMINHO_ARQUIVO = "emprestimos.json";
    private final Gson conversorJson = GsonUtil.createGson(); // Usando o GSON configurado
    private List<Emprestimo> emprestimos;

    public EmprestimoDAO() {
        emprestimos = new ArrayList<>();
        carregarDados();
    }

    private void carregarDados() {
        try (FileReader reader = new FileReader(CAMINHO_ARQUIVO)) {
            Type listType = new TypeToken<ArrayList<Emprestimo>>(){}.getType();
            emprestimos = conversorJson.fromJson(reader, listType);
            if (emprestimos == null) {
                emprestimos = new ArrayList<>();
            }
        } catch (Exception e) {
            System.out.println("Arquivo não encontrado ou inválido. Criando nova lista.");
            System.out.println("Detalhe do erro: " + e.getMessage());
            emprestimos = new ArrayList<>();
        }
    }

    private void salvarDados() {
        try (FileWriter writer = new FileWriter(CAMINHO_ARQUIVO)) {
            conversorJson.toJson(emprestimos, writer);
        } catch (Exception e) {
            System.err.println("Erro ao salvar empréstimos:");
            e.printStackTrace();
        }
    }

    @Override
    public void salvar(Emprestimo emprestimo) {
        if (emprestimo == null) {
            throw new IllegalArgumentException("Empréstimo não pode ser nulo");
        }

        if (!emprestimos.contains(emprestimo)) {
            emprestimos.add(emprestimo);
        }
        salvarDados();
    }

    @Override
    public Emprestimo buscarPorId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        return emprestimos.stream()
                .filter(e -> id.equals(e.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Emprestimo> listarTodos() {
        return new ArrayList<>(emprestimos);
    }

    @Override
    public void remover(String id) {
        if (id != null && !id.trim().isEmpty()) {
            emprestimos.removeIf(e -> id.equals(e.getId()));
            salvarDados();
        }
    }

    public List<Emprestimo> listarEmprestimosAtivos() {
        List<Emprestimo> ativos = new ArrayList<>();
        for (Emprestimo emp : emprestimos) {
            if (emp != null && !emp.isDevolvido()) {
                ativos.add(emp);
            }
        }
        return ativos;
    }

    public int countEmprestimosPorObra(String codigoObra) {
        if (codigoObra == null || codigoObra.trim().isEmpty()) {
            return 0;
        }
        return (int) emprestimos.stream()
                .filter(e -> e != null && e.getObra() != null
                        && codigoObra.equals(e.getObra().getCodigo()))
                .count();
    }
}