package biblioteca.model;

public class UsuarioSistema {
    private String login;
    private String senha;
    private PerfilUsuario perfil;

    public enum PerfilUsuario {
        ADMINISTRADOR, BIBLIOTECARIO, ESTAGIARIO
    }

    public UsuarioSistema(String login, String senha, PerfilUsuario perfil) {
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    // Getters
    public String getLogin() { return login; }
    public PerfilUsuario getPerfil() { return perfil; }

    // Método para verificar senha
    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }
}