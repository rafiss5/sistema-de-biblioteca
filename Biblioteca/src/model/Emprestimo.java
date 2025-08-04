package model;

import java.time.LocalDate;

public class Emprestimo {
    private String id;
    private String obraId;
    private Usuario usuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private boolean devolvido;

    // Construtor
    public Emprestimo(String id, String obraId, Usuario usuario, LocalDate dataEmprestimo) {
        this.id = id;
        this.obraId = obraId;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.devolvido = false;
    }

    // Getters e Setters
    public String getId() { return id; }
    public String getObraId() { return obraId; }
    public Usuario getUsuario() { return usuario; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public boolean isDevolvido() { return devolvido; }

    public void registrarDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
        this.devolvido = true;
    }

    public boolean isAtrasado() {
        LocalDate dataLimite = dataEmprestimo.plusDays(15);
        return devolvido ? dataDevolucao.isAfter(dataLimite) : LocalDate.now().isAfter(dataLimite);
    }

    public double getMulta() {
        return isAtrasado() ? 5.0 : 0.0;
    }
}