package model;

public class Revista extends Obra {
    private String issn;
    private int numero;

    public Revista(String codigo, String titulo, String autor, int anoPublicacao, String issn, int numero) {
        super(codigo, titulo, autor, anoPublicacao, "Revista");
        this.issn = issn;
        this.numero = numero;
    }

    @Override
    public int getTempoEmprestimo() {
        return 2;
    }

    // Getters
    public String getIssn() { return issn; }
    public int getNumero() { return numero; }
}