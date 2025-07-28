package biblioteca.view;

import biblioteca.model.UsuarioSistema.PerfilUsuario;
import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    private PerfilUsuario perfilUsuario;
    private JMenuBar barraMenu;
    private JMenu menuCadastros, menuOperacoes, menuRelatorios, menuAjuda;
    private JMenuItem itemMenuLivros, itemMenuUsuarios;
    private JMenuItem itemMenuEmprestimo, itemMenuDevolucao;
    private JMenuItem itemMenuRelatorios, itemMenuSobre;

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

        // Menu de Ajuda
        menuAjuda = new JMenu("Ajuda");
        itemMenuSobre = new JMenuItem("Sobre o Sistema");
        itemMenuSobre.addActionListener(e -> mostrarInformacoesSistema());
        menuAjuda.add(itemMenuSobre);

        barraMenu.add(menuCadastros);
        barraMenu.add(menuOperacoes);
        barraMenu.add(menuRelatorios);
        barraMenu.add(menuAjuda);
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
        JLabel titulo = new JLabel("Bem-vindo ao Sistema de Biblioteca", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        painelPrincipal.add(titulo, BorderLayout.CENTER);

        // Adiciona informação do perfil
        JLabel perfilLabel = new JLabel("Perfil: " + perfilUsuario.toString(), SwingConstants.RIGHT);
        painelPrincipal.add(perfilLabel, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    // Métodos para abrir as telas (mantidos iguais)
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
                        "Desenvolvido por: Equipe de Desenvolvimento\n"
                        ,
                "Sobre o Sistema",
                JOptionPane.INFORMATION_MESSAGE);
    }
}