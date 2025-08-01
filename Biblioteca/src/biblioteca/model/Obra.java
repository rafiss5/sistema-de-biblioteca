package biblioteca.model;

import java.time.LocalDate;

public abstract class Obra {
    private String codigo;
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private boolean emprestado;

    public Obra(String codigo, String titulo, String autor, int anoPublicacao) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.emprestado = false;
    }

    // Método abstrato que será implementado pelas classes filhas
    public abstract int getTempoEmprestimo();

    // Calcula a data de devolução com base na data de empréstimo
    public LocalDate calcularDataDevolucao(LocalDate dataEmprestimo) {
        return dataEmprestimo.plusDays(getTempoEmprestimo());
    }

    // Getters e Setters
    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnoPublicacao() { return anoPublicacao; }
    public boolean isEmprestado() { return emprestado; }

    public void setEmprestado(boolean emprestado) { this.emprestado = emprestado; }
}