package dao;

import java.util.List;

public interface Persistivel<T> {
    boolean salvar(T objeto);
    T buscarPorId(String id);
    List<T> listarTodos();
    boolean remover(String id);
}