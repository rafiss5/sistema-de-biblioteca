package biblioteca.view;

import biblioteca.controller.EmprestimoController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaDevolucao extends JFrame {
    private final EmprestimoController controller;
    private JTextField txtEmprestimoId;
    private JLabel lblMulta;
    private JButton btnPagar;
    private double multaAtual = 0.0;

    public TelaDevolucao(EmprestimoController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Registrar Devolução");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("ID do Empréstimo:"));
        txtEmprestimoId = new JTextField();
        panel.add(txtEmprestimoId);

        panel.add(new JLabel("Multa:"));
        lblMulta = new JLabel("R$ 0.00");
        panel.add(lblMulta);

        JButton btnDevolver = new JButton("Registrar Devolução");
        btnDevolver.addActionListener(this::registrarDevolucao);
        panel.add(btnDevolver);

        btnPagar = new JButton("Pagar Multa");
        btnPagar.addActionListener(this::pagarMulta);
        btnPagar.setEnabled(false);
        panel.add(btnPagar);

        add(panel);
    }

    private void registrarDevolucao(ActionEvent evt) {
        String id = txtEmprestimoId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Informe o ID do empréstimo",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        double multa = controller.registrarDevolucao(id);

        if (multa >= 0) {
            this.multaAtual = multa;
            lblMulta.setText(String.format("R$ %.2f", multa));
            btnPagar.setEnabled(multa > 0);

            JOptionPane.showMessageDialog(this,
                    "Devolução registrada com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Erro ao registrar devolução. Verifique o ID.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pagarMulta(ActionEvent evt) {
        String[] metodos = {"Dinheiro", "PIX", "Cartão", "Boleto"};
        String metodo = (String) JOptionPane.showInputDialog(
                this,
                "Valor: " + lblMulta.getText() + "\nSelecione o método:",
                "Pagamento de Multa",
                JOptionPane.QUESTION_MESSAGE,
                null,
                metodos,
                metodos[0]
        );

        if (metodo != null) {
            boolean sucesso = controller.registrarPagamento(
                    txtEmprestimoId.getText().trim(),
                    multaAtual,
                    metodo
            );

            if (sucesso) {
                JOptionPane.showMessageDialog(this,
                        "Pagamento registrado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                lblMulta.setText("R$ 0.00");
                multaAtual = 0.0;
                btnPagar.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Falha ao registrar pagamento",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}