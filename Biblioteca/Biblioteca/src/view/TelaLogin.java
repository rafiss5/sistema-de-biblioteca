package view;

import controller.*;
import model.UsuarioSistema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaLogin extends JFrame {
    private final UsuarioSistemaController controller;

    // Campos agora são atributos da classe
    private JTextField txtUsuario;
    private JPasswordField txtSenha;

    public TelaLogin() {
        this.controller = new UsuarioSistemaController();
        initComponents();
    }

    private void initComponents() {
        setTitle("Login - Sistema de Biblioteca");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new GridLayout(3, 2, 10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblUsuario = new JLabel("Usuário:");
        txtUsuario = new JTextField();

        JLabel lblSenha = new JLabel("Senha:");
        txtSenha = new JPasswordField();

        JButton btnLogin = new JButton("Entrar");
        btnLogin.addActionListener(this::realizarLogin);

        panelPrincipal.add(lblUsuario);
        panelPrincipal.add(txtUsuario);
        panelPrincipal.add(lblSenha);
        panelPrincipal.add(txtSenha);
        panelPrincipal.add(new JLabel()); // espaço vazio
        panelPrincipal.add(btnLogin);

        add(panelPrincipal);
    }

    private void realizarLogin(ActionEvent e) {
        String login = txtUsuario.getText();
        String senha = new String(txtSenha.getPassword());

        UsuarioSistema usuario = controller.autenticar(login, senha);

        if (usuario != null) {
            abrirTelaPrincipal(usuario);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Usuário ou senha incorretos!",
                    "Erro de login",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirTelaPrincipal(UsuarioSistema usuario) {
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal(
                    usuario.getPerfil(),
                    new ObraController(),
                    new UsuarioController(),
                    new EmprestimoController(),
                    new RelatorioController()
            ).setVisible(true);
        });
    }

}
