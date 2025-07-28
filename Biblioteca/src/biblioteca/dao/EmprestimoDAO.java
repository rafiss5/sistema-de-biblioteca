package biblioteca.dao;

import biblioteca.model.Emprestimo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO implements Persistivel<Emprestimo> {
    private static final String CAMINHO_ARQUIVO = "emprestimos.json";
    private Gson conversorjson = new Gson();
    private List<Emprestimo> emprestimos;

    public EmprestimoDAO() {
        emprestimos = new ArrayList<>();
        carregarDados();
    }

    private void carregarDados() {
        try (FileReader reader = new FileReader(CAMINHO_ARQUIVO)) {
            Type listType = new TypeToken<ArrayList<Emprestimo>>(){}.getType();
            emprestimos = conversorjson.fromJson(reader, listType);
            if (emprestimos == null) {
                emprestimos = new ArrayList<>();
            }
        } catch (Exception e) {
            System.out.println("Arquivo não encontrado ou inválido. Criando nova lista.");
            emprestimos = new ArrayList<>();
        }
    }

    private void salvarDados() {
        try (FileWriter writer = new FileWriter(CAMINHO_ARQUIVO)) {
            conversorjson.toJson(emprestimos, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void salvar(Emprestimo emprestimo) {
        if (!emprestimos.contains(emprestimo)) {
            emprestimos.add(emprestimo);
        }
        salvarDados();
    }

    @Override
    public Emprestimo buscarPorId(String id) {
        return emprestimos.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Emprestimo> listarTodos() {
        return new ArrayList<>(emprestimos);
    }

    @Override
    public void remover(String id) {
        emprestimos.removeIf(e -> e.getId().equals(id));
        salvarDados();
    }

    public List<Emprestimo> listarEmprestimosAtivos() {
        List<Emprestimo> ativos = new ArrayList<>();
        for (Emprestimo emp : emprestimos) {
            if (!emp.isDevolvido()) {
                ativos.add(emp);
            }
        }
        return ativos;
    }
}