
package controller;

public class ResultadoOperacao {
    private boolean sucesso;
    private String mensagem;
    private Object resultado;

    public ResultadoOperacao(boolean sucesso, String mensagem) {
        this(sucesso, mensagem, null);
    }

    public ResultadoOperacao(boolean sucesso, String mensagem, Object resultado) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.resultado = resultado;
    }

    // Getters
    public boolean isSucesso() { return sucesso; }
    public String getMensagem() { return mensagem; }
    public Object getResultado() { return resultado; }
}