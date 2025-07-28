package biblioteca.view;

import biblioteca.controller.BibliotecaController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaDevolucao extends JFrame {

    private JTextField tfEmprestimo;
    private JButton btnConfirmar, btnCancelar;

    public TelaDevolucao() {
        initComponents();
        configurarJanela();
    }

    public TelaDevolucao(BibliotecaController controller) {
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        panel.add(new JLabel("ID do Empréstimo:"));
        tfEmprestimo = new JTextField();
        panel.add(tfEmprestimo);

        btnConfirmar = new JButton("Confirmar Devolução");
        btnConfirmar.addActionListener(this::confirmarDevolucao);
        panel.add(btnConfirmar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> this.dispose());
        panel.add(btnCancelar);

        this.add(panel, BorderLayout.CENTER);
    }

    private void configurarJanela() {
        this.setTitle("Registrar Devolução");
        this.setSize(400, 150);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private void confirmarDevolucao(ActionEvent e) {
        // Implemente a lógica de devolução aqui
        JOptionPane.showMessageDialog(this, "Devolução registrada com sucesso!");
        this.dispose();
    }
}