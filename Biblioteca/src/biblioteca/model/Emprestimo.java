package biblioteca.model;

import java.time.LocalDate;

public class Emprestimo {
    private String id;
    private Obra obra;
    private Usuario usuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;
    private boolean devolvido;
    private boolean atrasado;
    private double multa;

    public Emprestimo(String id, Obra obra, Usuario usuario, LocalDate dataEmprestimo) {
        this.id = id;
        this.obra = obra;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = obra.calcularDataDevolucao(dataEmprestimo);
        this.devolvido = false;
        this.atrasado = false;
        this.multa = 0.0;
    }

    // Método para registrar devolução
    public void registrarDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucaoReal = dataDevolucao;
        this.devolvido = true;

        if (dataDevolucao.isAfter(dataDevolucaoPrevista)) {
            this.atrasado = true;
            long diasAtraso = dataDevolucao.toEpochDay() - dataDevolucaoPrevista.toEpochDay();
            this.multa = calcularMulta(diasAtraso);
            usuario.incrementarAtrasos();
        }
    }

    private double calcularMulta(long diasAtraso) {
        // Valor fictício - pode ser ajustado conforme regras da biblioteca
        return diasAtraso * 2.0;
    }

    // Getters
    public String getId() { return id; }
    public Obra getObra() { return obra; }
    public Usuario getUsuario() { return usuario; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }
    public LocalDate getDataDevolucaoReal() { return dataDevolucaoReal; }
    public boolean isDevolvido() { return devolvido; }
    public boolean isAtrasado() { return atrasado; }
    public double getMulta() { return multa; }
}