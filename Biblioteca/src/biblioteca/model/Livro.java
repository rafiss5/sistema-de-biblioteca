package biblioteca.model;

public class Livro extends Obra {
    private String isbn;
    private int edicao;

    public Livro(String codigo, String titulo, String autor, int anoPublicacao, String isbn, int edicao) {
        super(codigo, titulo, autor, anoPublicacao, "Livro");
        this.isbn = isbn;
        this.edicao = edicao;
    }

    @Override
    public int getTempoEmprestimo() {
        return 14; // Livros podem ser emprestados por 14 dias
    }

    // Getters
    public String getIsbn() { return isbn; }
    public int getEdicao() { return edicao; }
}