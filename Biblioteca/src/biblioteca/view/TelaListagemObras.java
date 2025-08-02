package biblioteca.view;

import biblioteca.controller.ObraController;
import biblioteca.model.Obra;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class TelaListagemObras extends JFrame {

    private DefaultTableModel model;
    private ObraController obraController;
    private JTable tabela;

    public TelaListagemObras(ObraController obraController) {
        this.obraController = obraController;
        configurarJanela();
        initComponents();
        atualizarTabela();
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

        tabela = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabela);

        // Botão Atualizar
        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> atualizarTabela());

        // Botão Remover
        JButton btnRemover = new JButton("Remover");
        btnRemover.addActionListener(this::removerObra);

        // Botão Fechar
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());

        // Painel de botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotoes.add(btnAtualizar);
        panelBotoes.add(btnRemover);
        panelBotoes.add(btnFechar);

        // Layout principal
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }

    private void atualizarTabela() {
        model.setRowCount(0); // Limpa a tabela

        // Obtém a lista atualizada de obras do controller
        List<Obra> obras = obraController.listarObras();

        // Adiciona cada obra na tabela
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

        // Mostra mensagem de sucesso
        JOptionPane.showMessageDialog(this,
                "Lista de obras atualizada com sucesso!",
                "Atualização",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void removerObra(ActionEvent e) {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma obra para remover",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigo = (String) model.getValueAt(linhaSelecionada, 0);
        String titulo = (String) model.getValueAt(linhaSelecionada, 1);

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja remover a obra:\n" + titulo + " (" + codigo + ")",
                "Confirmar Remoção",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                obraController.removerObra(codigo);
                atualizarTabela(); // Atualiza a tabela após remoção
                JOptionPane.showMessageDialog(this,
                        "Obra removida com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Erro ao remover obra: " + ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
 }
}
}