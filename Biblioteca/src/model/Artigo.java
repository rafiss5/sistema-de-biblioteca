package model;

public class Artigo extends Obra {
    private String doi;
    private String nomePeriodico;

    public Artigo(String codigo, String titulo, String autor, int anoPublicacao, String doi, String nomePeriodico) {
        super(codigo, titulo, autor, anoPublicacao, "Artigo");
        this.doi = doi;
        this.nomePeriodico = nomePeriodico;
    }

    @Override
    public int getTempoEmprestimo() {
        return 3; // Artigos podem ser emprestados por 3 dias
    }

    // Getters
    public String getDoi() { return doi; }
    public String getNomePeriodico() { return nomePeriodico; }
}