package biblioteca.view;

import biblioteca.controller.BibliotecaController;
import biblioteca.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class TelaCadastroUsuario extends JFrame {
    private BibliotecaController controller;

    public TelaCadastroUsuario() {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Cadastro de Usuário");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtMatricula = new JTextField();
        JTextField txtNome = new JTextField();
        JComboBox<Usuario.TipoUsuario> cbTipo = new JComboBox<>(Usuario.TipoUsuario.values());
        JTextField txtTelefone = new JTextField();
        JTextField txtEmail = new JTextField();

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            try {
                Usuario usuario = new Usuario(
                        txtMatricula.getText(),
                        txtNome.getText(),
                        (Usuario.TipoUsuario) cbTipo.getSelectedItem(),
                        txtTelefone.getText(),
                        txtEmail.getText()
                );
                controller.cadastrarUsuario(usuario);
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel("Matrícula:"));
        panel.add(txtMatricula);
        panel.add(new JLabel("Nome:"));
        panel.add(txtNome);
        panel.add(new JLabel("Tipo:"));
        panel.add(cbTipo);
        panel.add(new JLabel("Telefone:"));
        panel.add(txtTelefone);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel());
        panel.add(btnSalvar);

        add(panel);
    }
}