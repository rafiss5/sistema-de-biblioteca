package biblioteca.controller;

import biblioteca.dao.ObraDAO;
import biblioteca.model.Obra;
import biblioteca.model.Livro;
import biblioteca.model.Revista;
import biblioteca.model.Artigo;
import java.util.List;
import java.util.stream.Collectors;

public class ObraController {
    private final ObraDAO obraDAO;

    public ObraController() {
        this.obraDAO = new ObraDAO();
    }

    public void cadastrarObra(Obra obra) throws IllegalArgumentException {
        validarObra(obra);

        if (obraDAO.buscarPorId(obra.getCodigo()) != null) {
            throw new IllegalArgumentException("Já existe uma obra com este código");
        }

        obraDAO.salvar(obra);
    }

    public Obra buscarObra(String codigo) throws IllegalArgumentException {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("Código da obra não pode ser vazio");
        }

        Obra obra = obraDAO.buscarPorId(codigo);
        if (obra == null) {
            throw new IllegalArgumentException("Obra não encontrada");
        }
        return obra;
    }

    public List<Obra> listarObras() {
        return obraDAO.listarTodos();
    }

    public List<Obra> listarObrasDisponiveis() {
        return obraDAO.listarTodos().stream()
                .filter(obra -> !obra.isEmprestado())
                .collect(Collectors.toList());
    }

    public void removerObra(String codigo) throws IllegalStateException, IllegalArgumentException {
        Obra obra = buscarObra(codigo);

        if (obra.isEmprestado()) {
            throw new IllegalStateException("Não é possível remover obra emprestada");
        }

        obraDAO.remover(codigo);
    }

    private void validarObra(Obra obra) throws IllegalArgumentException {
        if (obra == null) {
            throw new IllegalArgumentException("Obra não pode ser nula");
        }

        if (obra.getCodigo() == null || obra.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("Código da obra é obrigatório");
        }

        if (obra.getTitulo() == null || obra.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("Título da obra é obrigatório");
        }

        if (obra.getAnoPublicacao() <= 0) {
            throw new IllegalArgumentException("Ano de publicação inválido");
        }

        // Validações específicas por tipo
        if (obra instanceof Livro) {
            Livro livro = (Livro) obra;
            if (livro.getIsbn() == null || livro.getIsbn().trim().isEmpty()) {
                throw new IllegalArgumentException("ISBN é obrigatório para livros");
            }
        } else if (obra instanceof Revista) {
            Revista revista = (Revista) obra;
            if (revista.getIssn() == null || revista.getIssn().trim().isEmpty()) {
                throw new IllegalArgumentException("ISSN é obrigatório para revistas");
            }
        } else if (obra instanceof Artigo) {
            Artigo artigo = (Artigo) obra;
            if (artigo.getDoi() == null || artigo.getDoi().trim().isEmpty()) {
                throw new IllegalArgumentException("DOI é obrigatório para artigos");
            }
        }
    }
}