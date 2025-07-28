package biblioteca.view;

import biblioteca.model.UsuarioSistema.PerfilUsuario;
import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    private PerfilUsuario perfilUsuario;
    private JMenuBar barraMenu;
    private JMenu menuCadastros, menuOperacoes, menuRelatorios, menuUsuario;
    private JMenuItem itemMenuLivros, itemMenuUsuarios;
    private JMenuItem itemMenuEmprestimo, itemMenuDevolucao;
    private JMenuItem itemMenuRelatorios, itemMenuSobre, itemMenuSair;

    public TelaPrincipal(PerfilUsuario perfilUsuario) {
        this.perfilUsuario = perfilUsuario;
        configurarJanela();
        criarComponentes();
        organizarLayout();
        ajustarPermissoes();
    }

    private void configurarJanela() {
        setTitle("Sistema de Biblioteca - [" + perfilUsuario.toString() + "]");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void criarComponentes() {
        barraMenu = new JMenuBar();

        // Menu de Cadastros
        menuCadastros = new JMenu("Cadastros");
        itemMenuLivros = new JMenuItem("Livros");
        itemMenuUsuarios = new JMenuItem("Usuários");

        itemMenuLivros.addActionListener(e -> abrirCadastroLivros());
        itemMenuUsuarios.addActionListener(e -> abrirCadastroUsuarios());

        menuCadastros.add(itemMenuLivros);
        menuCadastros.add(itemMenuUsuarios);

        // Menu de Operações
        menuOperacoes = new JMenu("Operações");
        itemMenuEmprestimo = new JMenuItem("Realizar Empréstimo");
        itemMenuDevolucao = new JMenuItem("Registrar Devolução");

        itemMenuEmprestimo.addActionListener(e -> abrirTelaEmprestimo());
        itemMenuDevolucao.addActionListener(e -> abrirTelaDevolucao());

        menuOperacoes.add(itemMenuEmprestimo);
        menuOperacoes.add(itemMenuDevolucao);

        // Menu de Relatórios
        menuRelatorios = new JMenu("Relatórios");
        itemMenuRelatorios = new JMenuItem("Gerar Relatórios");
        itemMenuRelatorios.addActionListener(e -> abrirRelatorios());
        menuRelatorios.add(itemMenuRelatorios);

        // Menu Usuário (novo)
        menuUsuario = new JMenu("Usuário");
        itemMenuSair = new JMenuItem("Trocar Usuário");
        itemMenuSair.addActionListener(e -> fazerLogout());
        menuUsuario.add(itemMenuSair);

        barraMenu.add(menuCadastros);
        barraMenu.add(menuOperacoes);
        barraMenu.add(menuRelatorios);
        barraMenu.add(Box.createHorizontalGlue()); // Espaço flexível
        barraMenu.add(menuUsuario); // Menu usuário alinhado à direita
    }

    private void ajustarPermissoes() {
        switch (perfilUsuario) {
            case ADMINISTRADOR:
                break;
            case BIBLIOTECARIO:
                itemMenuUsuarios.setEnabled(false);
                break;
            case ESTAGIARIO:
                itemMenuUsuarios.setEnabled(false);
                itemMenuRelatorios.setEnabled(false);
                break;
        }
    }

    private void organizarLayout() {
        setJMenuBar(barraMenu);

        JPanel painelPrincipal = new JPanel(new BorderLayout());

        // Painel superior com informações do usuário
        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblUsuario = new JLabel("Usuário: " + perfilUsuario.toString());
        painelSuperior.add(lblUsuario);

        // Conteúdo principal
        JLabel titulo = new JLabel("Bem-vindo ao Sistema de Biblioteca", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        painelPrincipal.add(painelSuperior, BorderLayout.NORTH);
        painelPrincipal.add(titulo, BorderLayout.CENTER);

        add(painelPrincipal);
    }

    private void fazerLogout() {
        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente sair e trocar de usuário?",
                "Confirmar saída",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            this.dispose();
            new TelaLogin().setVisible(true);
        }
    }

    // Métodos para abrir outras telas (mantidos)
    private void abrirCadastroLivros() {
        new TelaCadastroObra().setVisible(true);
    }

    private void abrirCadastroUsuarios() {
        new TelaCadastroUsuario().setVisible(true);
    }

    private void abrirTelaEmprestimo() {
        new TelaEmprestimo().setVisible(true);
    }

    private void abrirTelaDevolucao() {
        new TelaDevolucao().setVisible(true);
    }

    private void abrirRelatorios() {
        new TelaRelatorios().setVisible(true);
    }

    private void mostrarInformacoesSistema() {
        JOptionPane.showMessageDialog(this,
                "Sistema de Gerenciamento de Biblioteca\n" +
                        "Versão 2.0\n\n" +
                        "Perfil atual: " + perfilUsuario.toString() + "\n" +
                        "Desenvolvido por: Equipe de Desenvolvimento\n" +
                        "Contato: suporte@biblioteca.com",
                "Sobre o Sistema",
                JOptionPane.INFORMATION_MESSAGE);
    }
}