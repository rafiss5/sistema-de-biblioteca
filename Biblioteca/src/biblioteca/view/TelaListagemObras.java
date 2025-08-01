package biblioteca.view;

import biblioteca.controller.ObraController;
import biblioteca.model.Obra;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListagemObras extends JFrame {

    private DefaultTableModel model;
    private ObraController obraController;

    public TelaListagemObras(ObraController obraController) {
        this.obraController = obraController;
        configurarJanela();
        initComponents();
        atualizarTabela(); // atualiza tabela ao abrir
    }

    private void configurarJanela() {
        setTitle("Listagem de Obras da Biblioteca");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        String[] colunas = {"Código", "Título", "Autor", "Ano", "Tipo", "Status"};
        model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabela);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());

        JButton btnAtualizar = new JButton("Atualizar Lista");
        btnAtualizar.addActionListener(e -> atualizarTabela());

        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotoes.add(btnAtualizar);
        panelBotoes.add(btnFechar);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }

    public void atualizarTabela() {
        model.setRowCount(0); // limpa a tabela
        List<Obra> obras = obraController.listarObras();
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