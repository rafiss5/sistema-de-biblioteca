package biblioteca.model;

public class Artigo extends Obra {
    private String doi;
    private String nomePeriodico;

    public Artigo(String codigo, String titulo, String autor, int anoPublicacao, String doi, String nomePeriodico) {
        super(codigo, titulo, autor, anoPublicacao);
        this.doi = doi;
        this.nomePeriodico = nomePeriodico;
    }

    @Override
    public int getTempoEmprestimo() {
        return 2; // Artigos podem ser emprestados por 2 dias
    }

    // Getters e Setters específicos
    public String getDoi() { return doi; }
    public String getNomePeriodico() { return nomePeriodico; }
}