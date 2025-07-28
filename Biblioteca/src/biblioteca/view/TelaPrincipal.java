package biblioteca.view;

import biblioteca.controller.BibliotecaController;
import biblioteca.model.UsuarioSistema.PerfilUsuario;
import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    private PerfilUsuario perfilUsuario;
    private JMenuBar barraMenu;
    private JMenu menuCadastros, menuOperacoes, menuRelatorios, menuUsuario;
    private JMenuItem itemMenuCadastrarObra, itemMenuListarObras, itemMenuUsuarios, itemMenuListarUsuarios;
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

        // Submenu para Obras
        JMenu subMenuObras = new JMenu("Obras");
        itemMenuCadastrarObra = new JMenuItem("Cadastrar Obra");
        itemMenuListarObras = new JMenuItem("Listar Obras");
        itemMenuCadastrarObra.addActionListener(e -> abrirCadastroLivros());
        itemMenuListarObras.addActionListener(e -> abrirListagemObras());
        subMenuObras.add(itemMenuCadastrarObra);
        subMenuObras.add(itemMenuListarObras);

        // Submenu para Usuários
        JMenu subMenuUsuarios = new JMenu("Usuários");
        itemMenuUsuarios = new JMenuItem("Cadastrar Usuário");
        itemMenuListarUsuarios = new JMenuItem("Listar Usuários");
        itemMenuUsuarios.addActionListener(e -> abrirCadastroUsuarios());
        itemMenuListarUsuarios.addActionListener(e -> abrirListagemUsuarios());
        subMenuUsuarios.add(itemMenuUsuarios);
        subMenuUsuarios.add(itemMenuListarUsuarios);

        menuCadastros.add(subMenuObras);
        menuCadastros.add(subMenuUsuarios);

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

        // Menu Usuário - Atualizado com o item "Sobre"
        menuUsuario = new JMenu("Usuário");

        // Item Sobre o Sistema
        itemMenuSobre = new JMenuItem("Sobre");
        itemMenuSobre.addActionListener(e -> mostrarInformacoesSistema());

        // Item Trocar Usuário
        itemMenuSair = new JMenuItem("Trocar Usuário");
        itemMenuSair.addActionListener(e -> fazerLogout());

        // Adiciona os itens ao menu com separador
        menuUsuario.add(itemMenuSobre);
        menuUsuario.addSeparator();  // Linha divisória
        menuUsuario.add(itemMenuSair);

        barraMenu.add(menuCadastros);
        barraMenu.add(menuOperacoes);
        barraMenu.add(menuRelatorios);
        barraMenu.add(Box.createHorizontalGlue());
        barraMenu.add(menuUsuario);
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

    // Métodos para abrir telas
    private void abrirCadastroLivros() {
        new TelaCadastroObra().setVisible(true);
    }

    private void abrirListagemObras() {
        new TelaListagemObras(new BibliotecaController()).setVisible(true);
    }

    private void abrirCadastroUsuarios() {
        new TelaCadastroUsuario().setVisible(true);
    }

    private void abrirListagemUsuarios() {
        new TelaListagemUsuarios(new BibliotecaController()).setVisible(true);
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

    private void mostrarInformacoesSistema() {
        JOptionPane.showMessageDialog(this,
                "Sistema de Gerenciamento de Biblioteca\n" +
                        "Versão 1.0\n\n" +
                        "Perfil atual: " + perfilUsuario.toString() + "\n" +
                        "Desenvolvido por: Adson Ruan, Kauã Lopes e Rafael Araújo",
                "Sobre o Sistema",
                JOptionPane.INFORMATION_MESSAGE);
    }
}