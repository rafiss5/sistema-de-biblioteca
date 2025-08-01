package biblioteca.view;

import biblioteca.controller.UsuarioController;
import biblioteca.model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListagemUsuarios extends JFrame {
    private final UsuarioController usuarioController;

    public TelaListagemUsuarios(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        configurarJanela();
        initComponents();
    }

    private void configurarJanela() {
        setTitle("Listagem de Usuários");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Matrícula", "Nome", "Tipo", "Email", "Atrasos"}, 0);

        JTable tabela = new JTable(model);
        atualizarTabela(model);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> atualizarTabela(model));

        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(btnAtualizar, BorderLayout.SOUTH);
    }

    private void atualizarTabela(DefaultTableModel model) {
        model.setRowCount(0);
        List<Usuario> usuarios = usuarioController.listarUsuarios();
        usuarios.forEach(u -> model.addRow(new Object[]{
                u.getMatricula(),
                u.getNome(),
                u.getTipo().toString(),
                u.getEmail(),
                u.getAtrasos()
        }));
    }
}