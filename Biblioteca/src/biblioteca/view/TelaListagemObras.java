package biblioteca.view;

import biblioteca.controller.BibliotecaController;
import biblioteca.model.Obra;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListagemObras extends JFrame {

    public TelaListagemObras(BibliotecaController controller) {
        configurarJanela();
        initComponents(controller);
    }

    private void configurarJanela() {
        setTitle("Listagem de Obras da Biblioteca");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents(BibliotecaController controller) {
        // Modelo da tabela
        String[] colunas = {"Código", "Título", "Autor", "Ano", "Tipo", "Status"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável
            }
        };

        // Preenche a tabela com dados
        List<Obra> obras = controller.listarObras();
        for (Obra obra : obras) {
            Object[] linha = {
                    obra.getCodigo(),
                    obra.getTitulo(),
                    obra.getAutor(),
                    obra.getAnoPublicacao(),
                    obra.getClass().getSimpleName(),
                    obra.isEmprestado() ? "Emprestado" : "Disponível"
            };
            model.addRow(linha);
        }

        // Componentes
        JTable tabela = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabela);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());

        JButton btnAtualizar = new JButton("Atualizar Lista");
        btnAtualizar.addActionListener(e -> atualizarTabela(model, controller));

        // Layout
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotoes.add(btnAtualizar);
        panelBotoes.add(btnFechar);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }

    private void atualizarTabela(DefaultTableModel model, BibliotecaController controller) {
        model.setRowCount(0); // Limpa a tabela
        List<Obra> obras = controller.listarObras();
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
    }
}