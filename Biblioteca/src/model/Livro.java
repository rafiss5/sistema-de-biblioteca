package model;

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
        return 7;
    }

    // Getters
    public String getIsbn() { return isbn; }
    public int getEdicao() { return edicao; }
}