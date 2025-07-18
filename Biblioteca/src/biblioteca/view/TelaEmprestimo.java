package biblioteca.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaEmprestimo extends JFrame {

    private JTextField tfUsuario, tfLivro;
    private JButton btnConfirmar, btnCancelar;

    public TelaEmprestimo() {
        initComponents();
        configurarJanela();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        panel.add(new JLabel("ID do Usuário:"));
        tfUsuario = new JTextField();
        panel.add(tfUsuario);

        panel.add(new JLabel("ID do Livro:"));
        tfLivro = new JTextField();
        panel.add(tfLivro);

        btnConfirmar = new JButton("Confirmar Empréstimo");
        btnConfirmar.addActionListener(this::confirmarEmprestimo);
        panel.add(btnConfirmar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> this.dispose());
        panel.add(btnCancelar);

        this.add(panel, BorderLayout.CENTER);
    }

    private void configurarJanela() {
        this.setTitle("Registrar Empréstimo");
        this.setSize(400, 200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private void confirmarEmprestimo(ActionEvent e) {
        // Implemente a lógica de empréstimo aqui
        JOptionPane.showMessageDialog(this, "Empréstimo registrado com sucesso!");
        this.dispose();
    }
}