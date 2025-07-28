package biblioteca.model;

public class Revista extends Obra {
    private String issn;
    private int numero;

    public Revista(String codigo, String titulo, String autor, int anoPublicacao, String issn, int numero) {
        super(codigo, titulo, autor, anoPublicacao);
        this.issn = issn;
        this.numero = numero;
    }

    @Override
    public int getTempoEmprestimo() {
        return 3; // Revistas podem ser emprestadas por 3 dias
    }

    // Getters e Setters específicos
    public String getIssn() { return issn; }
    public int getNumero() { return numero; }
}