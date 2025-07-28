package biblioteca.view;

import biblioteca.controller.BibliotecaController;
import biblioteca.model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class TelaListagemUsuarios extends JFrame {

    public TelaListagemUsuarios(BibliotecaController controller) {
        configurarJanela();
        initComponents(controller);
    }

    private void configurarJanela() {
        setTitle("Listagem de Usuários Cadastrados");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents(BibliotecaController controller) {
        // Modelo da tabela
        String[] colunas = {"Matrícula", "Nome", "Tipo", "Telefone", "Email", "Atrasos"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável
            }
        };

        // Preenche a tabela com dados
        List<Usuario> usuarios = controller.listarUsuarios();
        for (Usuario usuario : usuarios) {
            Object[] linha = {
                    usuario.getMatricula(),
                    usuario.getNome(),
                    usuario.getTipo().toString(),
                    usuario.getTelefone(),
                    usuario.getEmail(),
                    usuario.getAtrasos()
            };
            model.addRow(linha);
        }

        // Componentes
        JTable tabela = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabela);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener((ActionEvent e) -> {
            dispose();
        });

        JButton btnAtualizar = new JButton("Atualizar Lista");
        btnAtualizar.addActionListener((ActionEvent e) -> {
            atualizarTabela(model, controller);
        });

        // Layout
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotoes.add(btnAtualizar);
        panelBotoes.add(btnFechar);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }

    private void atualizarTabela(DefaultTableModel model, BibliotecaController controller) {
        model.setRowCount(0); // Limpa a tabela
        List<Usuario> usuarios = controller.listarUsuarios();
        for (Usuario usuario : usuarios) {
            model.addRow(new Object[]{
                    usuario.getMatricula(),
                    usuario.getNome(),
                    usuario.getTipo(),
                    usuario.getTelefone(),
                    usuario.getEmail(),
                    usuario.getAtrasos()
            });
        }
    }
}