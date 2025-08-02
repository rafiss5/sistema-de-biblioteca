package biblioteca.model;

import java.time.LocalDate;

public class PagamentoMulta {
    private String id;
    private double valor;
    private LocalDate data;
    private String metodo; // DINHEIRO, PIX, CARTAO
    private Usuario usuario;

    public PagamentoMulta(String id, double valor, LocalDate data, String metodo, Usuario usuario) {
        this.id = id;
        this.valor = valor;
        this.data = data;
        this.metodo = metodo;
        this.usuario = usuario;
    }

    // Getters
    public String getId() { return id; }
    public double getValor() { return valor; }
    public LocalDate getData() { return data; }
    public String getMetodo() { return metodo; }
    public Usuario getUsuario() { return usuario; }
}
