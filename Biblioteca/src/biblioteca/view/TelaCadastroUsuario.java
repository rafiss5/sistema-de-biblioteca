package biblioteca.view;

import biblioteca.controller.BibliotecaController;
import biblioteca.model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastroUsuario extends JFrame {
    private BibliotecaController controller;

    public TelaCadastroUsuario() {
        this.controller = new BibliotecaController();
        initComponents();
    }

    private void initComponents() {
        setTitle("Cadastro de Usuário");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtMatricula = new JTextField();
        JTextField txtNome = new JTextField();
        JComboBox<Usuario.TipoUsuario> cbTipo = new JComboBox<>(Usuario.TipoUsuario.values());
        JTextField txtTelefone = new JTextField();
        JTextField txtEmail = new JTextField();

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario(txtMatricula, txtNome, cbTipo, txtTelefone, txtEmail);
            }
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel.add(new JLabel("Matrícula:*"));
        panel.add(txtMatricula);
        panel.add(new JLabel("Nome:*"));
        panel.add(txtNome);
        panel.add(new JLabel("Tipo:"));
        panel.add(cbTipo);
        panel.add(new JLabel("Telefone:"));
        panel.add(txtTelefone);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(btnCancelar);
        panel.add(btnSalvar);

        add(panel);
    }

    private void cadastrarUsuario(JTextField txtMatricula, JTextField txtNome,
                                  JComboBox<Usuario.TipoUsuario> cbTipo,
                                  JTextField txtTelefone, JTextField txtEmail) {
        try {
            // Validação dos campos obrigatórios
            if (txtMatricula.getText().trim().isEmpty()) {
                mostrarErro("Matrícula é obrigatória!", txtMatricula);
                return;
            }

            if (txtNome.getText().trim().isEmpty()) {
                mostrarErro("Nome é obrigatório!", txtNome);
                return;
            }

            // Verifica se matrícula contém apenas números
            if (!txtMatricula.getText().trim().matches("\\d+")) {
                mostrarErro("Matrícula deve conter apenas números!", txtMatricula);
                return;
            }

            Usuario usuario = new Usuario(
                    txtMatricula.getText().trim(),
                    txtNome.getText().trim(),
                    (Usuario.TipoUsuario) cbTipo.getSelectedItem(),
                    txtTelefone.getText().trim(),
                    txtEmail.getText().trim()
            );

            if (controller.cadastrarUsuario(usuario)) {
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
                dispose();
            } else {
                int opcao = JOptionPane.showConfirmDialog(
                        this,
                        "Matrícula " + txtMatricula.getText().trim() + " já existe.\nDeseja editar o usuário existente?",
                        "Matrícula Existente",
                        JOptionPane.YES_NO_OPTION);

                if (opcao == JOptionPane.YES_OPTION) {
                    // Aqui você pode implementar a edição do usuário existente
                    // Por exemplo: abrir a tela de edição com os dados do usuário
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void mostrarErro(String mensagem, JComponent campo) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
        campo.requestFocus();
    }
}