package biblioteca.view;

import biblioteca.controller.BibliotecaController;
import biblioteca.model.Obra;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListagemObras extends JFrame {
    public TelaListagemObras(BibliotecaController controller) {
        initComponents(controller);
    }

    private void initComponents(BibliotecaController controller) {
        setTitle("Listagem de Obras");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        List<Obra> obras = controller.listarObras();
        String[] colunas = {"Código", "Título", "Autor", "Ano", "Tipo", "Status"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        for (Obra obra : obras) {
            model.addRow(new Object[]{
                    obra.getCodigo(),
                    obra.getTitulo(),
                    obra.getAutor(),
                    obra.getAnoPublicacao(),
                    obra.getClass().getSimpleName(),
                    obra.isEmprestado() ? "Emprestado" : "Disponível"
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