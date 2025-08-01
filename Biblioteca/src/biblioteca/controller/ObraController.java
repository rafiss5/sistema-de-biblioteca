package biblioteca.controller;

import biblioteca.dao.ObraDAO;
import biblioteca.model.Obra;
import java.util.List;

public class ObraController {
    private final ObraDAO obraDAO;

    public ObraController() {
        this.obraDAO = new ObraDAO();
    }

    public void cadastrarObra(Obra obra) {
        obraDAO.salvar(obra);
    }

    public Obra buscarObra(String codigo) {
        return obraDAO.buscarPorId(codigo);
    }

    public List<Obra> listarObras() {
        return obraDAO.listarTodos();
    }

    public void removerObra(String codigo) {
        obraDAO.remover(codigo);
    }
}