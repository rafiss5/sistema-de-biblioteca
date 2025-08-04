package controller;

import dao.ObraDAO;
import model.Obra;
import model.Livro;
import model.Revista;
import model.Artigo;

import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class ObraController {
    private final ObraDAO obraDAO;

    public ObraController() {
        this.obraDAO = new ObraDAO();
    }

    /**
     * Cadastra uma nova obra no sistema
     * @param obra Obra a ser cadastrada
     * @throws IllegalArgumentException Se a obra for inválida ou já existir
     */
    public void cadastrarObra(Obra obra) throws IllegalArgumentException {
        validarObra(obra);

        if (obraExiste(obra.getCodigo())) {
            throw new IllegalArgumentException("Já existe uma obra cadastrada com o código: " + obra.getCodigo());
        }

        if (!obraDAO.salvar(obra)) {
            throw new IllegalStateException("Falha ao salvar obra no banco de dados");
        }
    }

    /**
     * Busca uma obra pelo código
     * @param codigo Código da obra
     * @return Obra encontrada
     * @throws IllegalArgumentException Se o código for inválido ou obra não encontrada
     */
    public Obra buscarObra(String codigo) throws IllegalArgumentException {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("Código da obra não pode ser vazio");
        }

        Obra obra = obraDAO.buscarPorId(codigo);
        if (obra == null) {
            throw new IllegalArgumentException("Obra não encontrada com código: " + codigo);
        }
        return obra;
    }

    /**
     * Lista todas as obras cadastradas
     * @return Lista de obras
     */
    public List<Obra> listarObras() {
        return obraDAO.listarTodos();
    }

    /**
     * Lista apenas obras disponíveis para empréstimo
     * @return Lista de obras disponíveis
     */
    public List<Obra> listarObrasDisponiveis() {
        return obraDAO.listarTodos().stream()
                .filter(obra -> !obra.isEmprestado())
                .collect(Collectors.toList());
    }

    /**
     * Remove uma obra do sistema
     * @param codigo Código da obra a ser removida
     * @throws IllegalArgumentException Se o código for inválido ou obra não encontrada
     * @throws IllegalStateException Se a obra estiver emprestada
     */
    public void removerObra(String codigo) throws IllegalArgumentException, IllegalStateException {
        Obra obra = buscarObra(codigo);

        if (obra.isEmprestado()) {
            throw new IllegalStateException("Não é possível remover obra emprestada. Código: " + codigo);
        }

        if (!obraDAO.remover(codigo)) {
            throw new IllegalStateException("Falha ao remover obra do banco de dados");
        }
    }

    /**
     * Verifica se uma obra existe
     * @param codigo Código da obra
     * @return true se existir, false caso contrário
     */
    public boolean obraExiste(String codigo) {
        try {
            return obraDAO.buscarPorId(codigo) != null;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Valida os dados de uma obra
     * @param obra Obra a ser validada
     * @throws IllegalArgumentException Se algum dado for inválido
     */
    private void validarObra(Obra obra) throws IllegalArgumentException {
        if (obra == null) {
            throw new IllegalArgumentException("Obra não pode ser nula");
        }

        // Validações básicas
        if (obra.getCodigo() == null || obra.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("Código da obra é obrigatório");
        }

        if (obra.getTitulo() == null || obra.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("Título da obra é obrigatório");
        }

        if (obra.getAutor() == null || obra.getAutor().trim().isEmpty()) {
            throw new IllegalArgumentException("Autor da obra é obrigatório");
        }

        if (obra.getAnoPublicacao() <= 0) {
            throw new IllegalArgumentException("Ano de publicação inválido: " + obra.getAnoPublicacao());
        }

        // Validações específicas por tipo
        if (obra instanceof Livro) {
            Livro livro = (Livro) obra;
            if (livro.getIsbn() == null || livro.getIsbn().trim().isEmpty()) {
                throw new IllegalArgumentException("ISBN é obrigatório para livros");
            }
            if (livro.getEdicao() <= 0) {
                throw new IllegalArgumentException("Edição do livro deve ser maior que zero");
            }
        } else if (obra instanceof Revista) {
            Revista revista = (Revista) obra;
            if (revista.getIssn() == null || revista.getIssn().trim().isEmpty()) {
                throw new IllegalArgumentException("ISSN é obrigatório para revistas");
            }
            if (revista.getNumero() <= 0) {
                throw new IllegalArgumentException("Número da revista deve ser maior que zero");
            }
        } else if (obra instanceof Artigo) {
            Artigo artigo = (Artigo) obra;
            if (artigo.getDoi() == null || artigo.getDoi().trim().isEmpty()) {
                throw new IllegalArgumentException("DOI é obrigatório para artigos");
            }
            if (artigo.getNomePeriodico() == null || artigo.getNomePeriodico().trim().isEmpty()) {
                throw new IllegalArgumentException("Nome do periódico é obrigatório para artigos");
            }
        }
    }
}