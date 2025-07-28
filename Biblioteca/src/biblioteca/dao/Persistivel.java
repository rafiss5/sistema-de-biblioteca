package biblioteca.dao;

import java.util.List;

public interface Persistivel<T> {
    void salvar(T objeto);
    T buscarPorId(String id);
    List<T> listarTodos();
    void remover(String id);
}