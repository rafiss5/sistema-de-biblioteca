package biblioteca.view;

import biblioteca.controller.UsuarioController;
import biblioteca.model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaListagemUsuarios extends JFrame {

    private DefaultTableModel model;
    private UsuarioController usuarioController;
    private JTable tabela;

    public TelaListagemUsuarios(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        configurarJanela();
        initComponents();
        atualizarTabela();
    }

    private void configurarJanela() {
        setTitle("Listagem de Usuários");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        String[] colunas = {"Matrícula", "Nome", "Tipo", "Email", "Telefone", "Atrasos"};
        model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabela);

        // Botões
        JButton btnAtualizar = new JButton("Atualizar Lista");
        btnAtualizar.addActionListener(e -> atualizarTabela());

        JButton btnRemover = new JButton("Remover Usuário");
        btnRemover.addActionListener(this::removerUsuario);

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
        model.setRowCount(0);
        usuarioController.listarUsuarios().forEach(this::adicionarUsuarioNaTabela);
    }

    private void adicionarUsuarioNaTabela(Usuario usuario) {
        model.addRow(new Object[]{
                usuario.getMatricula(),
                usuario.getNome(),
                usuario.getTipo().toString(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getAtrasos()
        });
    }

    private void removerUsuario(ActionEvent e) {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um usuário para remover",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String matricula = (String) model.getValueAt(linhaSelecionada, 0);
        String nome = (String) model.getValueAt(linhaSelecionada, 1);

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja remover o usuário:\n" + nome + " (" + matricula + ")",
                "Confirmar Remoção",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            boolean removido = usuarioController.removerUsuario(matricula);
            if (removido) {
                atualizarTabela();
                JOptionPane.showMessageDialog(this,
                        "Usuário removido com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            }
 }
}
}