package model;

import com.google.gson.annotations.SerializedName;

public abstract class Obra {
    private String codigo;
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private boolean emprestado;

    public Obra(String codigo, String titulo, String autor, int anoPublicacao, String tipoObra) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.emprestado = false;
    }

    public abstract int getTempoEmprestimo();

    // Getters
    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnoPublicacao() { return anoPublicacao; }
    public boolean isEmprestado() { return emprestado; }

    // Setter apenas para emprestado
    public void setEmprestado(boolean emprestado) { this.emprestado = emprestado; }
}