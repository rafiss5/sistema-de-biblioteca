package view;

import controller.EmprestimoController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaEmprestimo extends JFrame {
    private final EmprestimoController controller;
    private JTextField txtObraId, txtUsuarioId;

    public TelaEmprestimo(EmprestimoController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Realizar Empréstimo");
        setSize(400, 150);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Código da Obra:"));
        txtObraId = new JTextField();
        add(txtObraId);

        add(new JLabel("Matrícula do Usuário:"));
        txtUsuarioId = new JTextField();
        add(txtUsuarioId);

        JButton btnEmprestar = new JButton("Realizar Empréstimo");
        btnEmprestar.addActionListener(this::realizarEmprestimo);
        add(btnEmprestar);
    }

    private void realizarEmprestimo(ActionEvent evt) {
        String obraId = txtObraId.getText();
        String usuarioId = txtUsuarioId.getText();

        String idEmprestimo = controller.realizarEmprestimo(obraId, usuarioId);

        if (idEmprestimo != null) {
            JOptionPane.showMessageDialog(this, "Empréstimo realizado!\nID: " + idEmprestimo);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível realizar o empréstimo.\nVerifique se a obra está disponível e o usuário elegível.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}