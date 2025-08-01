package biblioteca.model;

public class Livro extends Obra {
    private String isbn;
    private int edicao;

    public Livro(String codigo, String titulo, String autor, int anoPublicacao, String isbn, int edicao) {
        super(codigo, titulo, autor, anoPublicacao);
        this.isbn = isbn;
        this.edicao = edicao;
    }

    @Override
    public int getTempoEmprestimo() {
        return 7; // Livros podem ser emprestados por 7 dias
    }

    // Getters e Setters específicos
    public String getIsbn() { return isbn; }
    public int getEdicao() { return edicao; }
}
