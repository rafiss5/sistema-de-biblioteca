package biblioteca.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {

    // Componentes do menu
    private JMenuBar menuBar;
    private JMenu mnCadastros, mnOperacoes, mnRelatorios, mnAjuda;
    private JMenuItem miLivros, miUsuarios, miEmprestimo, miDevolucao, miRelatorios, miSobre;

    public TelaPrincipal() {
        initComponents();
        configurarJanela();
    }

    private void initComponents() {

        menuBar = new JMenuBar();


        mnCadastros = new JMenu("Cadastros");
        miLivros = new JMenuItem("Livros");
        miUsuarios = new JMenuItem("Usuários");

        miLivros.addActionListener(e -> abrirTelaCadastroLivros());
        miUsuarios.addActionListener(e -> abrirTelaCadastroUsuarios());

        mnCadastros.add(miLivros);
        mnCadastros.add(miUsuarios);

        // Menu Operações
        mnOperacoes = new JMenu("Operações");
        miEmprestimo = new JMenuItem("Realizar Empréstimo");
        miDevolucao = new JMenuItem("Registrar Devolução");

        miEmprestimo.addActionListener(e -> abrirTelaEmprestimo());
        miDevolucao.addActionListener(e -> abrirTelaDevolucao());

        mnOperacoes.add(miEmprestimo);
        mnOperacoes.add(miDevolucao);

        // Menu Relatórios
        mnRelatorios = new JMenu("Relatórios");
        miRelatorios = new JMenuItem("Gerar Relatórios");
        miRelatorios.addActionListener(e -> abrirTelaRelatorios());
        mnRelatorios.add(miRelatorios);

        // Menu Ajuda
        mnAjuda = new JMenu("Ajuda");
        miSobre = new JMenuItem("Sobre");
        miSobre.addActionListener(e -> mostrarSobre());
        mnAjuda.add(miSobre);

        // Adiciona menus à barra
        menuBar.add(mnCadastros);
        menuBar.add(mnOperacoes);
        menuBar.add(mnRelatorios);
        menuBar.add(mnAjuda);

        this.setJMenuBar(menuBar);

        // Painel principal (pode ser customizado conforme necessidade)
        JPanel panel = new JPanel(new BorderLayout());
        JLabel lblBoasVindas = new JLabel("- Sistema de Gerenciamento de Biblioteca -", SwingConstants.CENTER);
        lblBoasVindas.setFont(new Font("Arial", Font.BOLD, 19));
        panel.add(lblBoasVindas, BorderLayout.CENTER);

        this.add(panel);
    }

    private void configurarJanela() {
        this.setTitle("Sistema Biblioteca - Tela Principal");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    // Métodos para abrir telas secundárias
    private void abrirTelaCadastroLivros() {
        TelaCadastroObra telaCadastroLivros = new TelaCadastroObra();
        telaCadastroLivros.setVisible(true);
    }

    private void abrirTelaCadastroUsuarios() {
        TelaCadastroUsuario telaCadastroUsuarios = new TelaCadastroUsuario();
        telaCadastroUsuarios.setVisible(true);
    }

    private void abrirTelaEmprestimo() {
        TelaEmprestimo telaEmprestimo = new TelaEmprestimo();
        telaEmprestimo.setVisible(true);
    }

    private void abrirTelaDevolucao() {
        TelaDevolucao telaDevolucao = new TelaDevolucao();
        telaDevolucao.setVisible(true);
    }

    private void abrirTelaRelatorios() {
        TelaRelatorios telaRelatorios = new TelaRelatorios();
        telaRelatorios.setVisible(true);
    }

    private void mostrarSobre() {
        JOptionPane.showMessageDialog(this,
                "Sistema de Biblioteca\nVersão 1.0\n\nDesenvolvido por Rafael Lima",
                "Sobre",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    }
}
