package biblioteca.view;

import biblioteca.dao.UsuarioSistemaDAO;
import biblioteca.model.UsuarioSistema;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {

    private UsuarioSistemaDAO usuarioSistemaDAO;

    public TelaLogin() {
        this.usuarioSistemaDAO = new UsuarioSistemaDAO();
        initComponents();
    }

    private void initComponents() {
        setTitle("Login - Sistema de Biblioteca");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela


        JPanel panelPrincipal = new JPanel(new GridLayout(3, 2, 10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblUsuario = new JLabel("Usuário:");
        JTextField txtUsuario = new JTextField();

        JLabel lblSenha = new JLabel("Senha:");
        JPasswordField txtSenha = new JPasswordField();

        JButton btnLogin = new JButton("Entrar");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = txtUsuario.getText();
                String senha = new String(txtSenha.getPassword());

                // Verifica credenciais
                UsuarioSistema usuario = usuarioSistemaDAO.autenticar(login, senha);

                if (usuario != null) {
                    // Login bem-sucedido: abre a tela principal e fecha o login
                    new TelaPrincipal(usuario.getPerfil()).setVisible(true);
                    dispose(); // Fecha a tela de login
                } else {
                    JOptionPane.showMessageDialog(
                            TelaLogin.this,
                            "Usuário ou senha incorretos!",
                            "Erro de login",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        // Adiciona componentes ao painel
        panelPrincipal.add(lblUsuario);
        panelPrincipal.add(txtUsuario);
        panelPrincipal.add(lblSenha);
        panelPrincipal.add(txtSenha);
        panelPrincipal.add(new JLabel()); // Espaço vazio
        panelPrincipal.add(btnLogin);

        // Adiciona o painel à janela
        add(panelPrincipal);
    }

    // Ponto de entrada do sistema (main)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaLogin().setVisible(true);
            }
        });
    }
}