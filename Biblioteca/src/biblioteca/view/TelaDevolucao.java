package biblioteca.view;

import biblioteca.controller.EmprestimoController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaDevolucao extends JFrame {
    private final EmprestimoController emprestimoController;
    private JTextField tfEmprestimo;
    private JButton btnConfirmar, btnCancelar;

    public TelaDevolucao(EmprestimoController emprestimoController) {
        this.emprestimoController = emprestimoController;
        initComponents();
        configurarJanela();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("ID do Empréstimo:"));
        tfEmprestimo = new JTextField();
        panel.add(tfEmprestimo);

        // Espaço vazio para layout
        panel.add(new JLabel());
        panel.add(new JLabel());

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
        String idEmprestimo = tfEmprestimo.getText().trim();

        if (idEmprestimo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Informe o ID do empréstimo!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double multa = emprestimoController.registrarDevolucao(idEmprestimo);

            if (multa > 0) {
                JOptionPane.showMessageDialog(this,
                        String.format("Devolução registrada com sucesso!\nMulta aplicada: R$ %.2f", multa),
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Devolução registrada com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            this.dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao registrar devolução: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}