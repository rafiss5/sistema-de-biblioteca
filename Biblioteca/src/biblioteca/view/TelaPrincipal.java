package biblioteca.view;

import biblioteca.controller.*;
import biblioteca.model.UsuarioSistema.PerfilUsuario;
import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    private final PerfilUsuario perfilUsuario;
    private final ObraController obraController;
    private final UsuarioController usuarioController;
    private final EmprestimoController emprestimoController;
    private final RelatorioController relatorioController;

    // Componentes de menu
    private JMenuBar barraMenu;
    private JMenu menuCadastros, menuOperacoes, menuRelatorios, menuUsuario;
    private JMenuItem itemMenuCadastrarObra, itemMenuListarObras, itemMenuUsuarios, itemMenuListarUsuarios;
    private JMenuItem itemMenuEmprestimo, itemMenuDevolucao;
    private JMenuItem itemMenuRelatorios, itemMenuSobre, itemMenuSair;

    public TelaPrincipal(PerfilUsuario perfilUsuario,
                         ObraController obraController,
                         UsuarioController usuarioController,
                         EmprestimoController emprestimoController,
                         RelatorioController relatorioController) {

        this.perfilUsuario = perfilUsuario;
        this.obraController = obraController;
        this.usuarioController = usuarioController;
        this.emprestimoController = emprestimoController;
        this.relatorioController = relatorioController;

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

        // Menu Cadastros
        menuCadastros = new JMenu("Cadastros");

        JMenu subMenuObras = new JMenu("Obras");
        itemMenuCadastrarObra = new JMenuItem("Cadastrar Obra");
        itemMenuListarObras = new JMenuItem("Listar Obras");
        itemMenuCadastrarObra.addActionListener(e -> abrirCadastroObra());
        itemMenuListarObras.addActionListener(e -> abrirListagemObras());
        subMenuObras.add(itemMenuCadastrarObra);
        subMenuObras.add(itemMenuListarObras);

        JMenu subMenuUsuarios = new JMenu("Usuários");
        itemMenuUsuarios = new JMenuItem("Cadastrar Usuário");
        itemMenuListarUsuarios = new JMenuItem("Listar Usuários");
        itemMenuUsuarios.addActionListener(e -> abrirCadastroUsuario());
        itemMenuListarUsuarios.addActionListener(e -> abrirListagemUsuarios());
        subMenuUsuarios.add(itemMenuUsuarios);
        subMenuUsuarios.add(itemMenuListarUsuarios);

        menuCadastros.add(subMenuObras);
        menuCadastros.add(subMenuUsuarios);

        // Menu Operações
        menuOperacoes = new JMenu("Operações");
        itemMenuEmprestimo = new JMenuItem("Realizar Empréstimo");
        itemMenuDevolucao = new JMenuItem("Registrar Devolução");
        itemMenuEmprestimo.addActionListener(e -> abrirTelaEmprestimo());
        itemMenuDevolucao.addActionListener(e -> abrirTelaDevolucao());
        menuOperacoes.add(itemMenuEmprestimo);
        menuOperacoes.add(itemMenuDevolucao);

        // Menu Relatórios
        menuRelatorios = new JMenu("Relatórios");
        itemMenuRelatorios = new JMenuItem("Gerar Relatórios");
        itemMenuRelatorios.addActionListener(e -> abrirRelatorios());
        menuRelatorios.add(itemMenuRelatorios);

        // Menu Usuário
        menuUsuario = new JMenu("Usuário");
        itemMenuSobre = new JMenuItem("Sobre");
        itemMenuSobre.addActionListener(e -> mostrarInformacoesSistema());
        itemMenuSair = new JMenuItem("Trocar Usuário");
        itemMenuSair.addActionListener(e -> fazerLogout());
        menuUsuario.add(itemMenuSobre);
        menuUsuario.addSeparator();
        menuUsuario.add(itemMenuSair);

        // Montagem da barra de menus
        barraMenu.add(menuCadastros);
        barraMenu.add(menuOperacoes);
        barraMenu.add(menuRelatorios);
        barraMenu.add(Box.createHorizontalGlue());
        barraMenu.add(menuUsuario);
    }

    private void ajustarPermissoes() {
        switch (perfilUsuario) {
            case ADMINISTRADOR:
                // Todos os menus habilitados
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
        titulo.setFont(new Font("Arial", Font.BOLD, 25));

        painelPrincipal.add(painelSuperior, BorderLayout.NORTH);
        painelPrincipal.add(titulo, BorderLayout.CENTER);

        add(painelPrincipal);
    }

    private void abrirCadastroObra() {
        new TelaCadastroObra(obraController).setVisible(true);
    }

    private void abrirListagemObras() {
        new TelaListagemObras(obraController).setVisible(true);
    }

    private void abrirCadastroUsuario() {
        new TelaCadastroUsuario(usuarioController).setVisible(true);
    }

    private void abrirListagemUsuarios() {
        new TelaListagemUsuarios(usuarioController).setVisible(true);
    }

    private void abrirTelaEmprestimo() {
        new TelaEmprestimo(emprestimoController).setVisible(true);
    }

    private void abrirTelaDevolucao() {
        new TelaDevolucao(emprestimoController).setVisible(true);
    }

    private void abrirRelatorios() {
        new TelaRelatorios(relatorioController).setVisible(true);
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