package biblioteca.model;

public class Usuario {
    private String matricula;
    private String nome;
    private TipoUsuario tipo;
    private String telefone;
    private String email;
    private int atrasos;

    public enum TipoUsuario {
        ALUNO, PROFESSOR, SERVIDOR
    }

    public Usuario(String matricula, String nome, TipoUsuario tipo, String telefone, String email) {
        this.matricula = matricula;
        this.nome = nome;
        this.tipo = tipo;
        this.telefone = telefone;
        this.email = email;
        this.atrasos = 0;
    }

    // Getters e Setters
    public String getMatricula() { return matricula; }
    public String getNome() { return nome; }
    public TipoUsuario getTipo() { return tipo; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }
    public int getAtrasos() { return atrasos; }
    public void setNome(String nome) { this.nome = nome; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }


    public void incrementarAtrasos() { this.atrasos++; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEmail(String email) { this.email = email; }
}