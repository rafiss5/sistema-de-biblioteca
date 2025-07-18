package biblioteca.view;

import biblioteca.controller.BibliotecaController;
import biblioteca.model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListagemUsuarios extends JFrame {
    public TelaListagemUsuarios(BibliotecaController controller) {
        initComponents(controller);
    }

    private void initComponents(BibliotecaController controller) {
        setTitle("Listagem de Usuários");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        List<Usuario> usuarios = controller.listarUsuarios();
        String[] colunas = {"Matrícula", "Nome", "Tipo", "Telefone", "Email", "Atrasos"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        for (Usuario usuario : usuarios) {
            model.addRow(new Object[]{
                    usuario.getMatricula(),
                    usuario.getNome(),
                    usuario.getTipo().toString(),
                    usuario.getTelefone(),
                    usuario.getEmail(),
                    usuario.getAtrasos()
            });
        }

        JTable tabela = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabela);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnFechar, BorderLayout.SOUTH);

        add(panel);
    }
}