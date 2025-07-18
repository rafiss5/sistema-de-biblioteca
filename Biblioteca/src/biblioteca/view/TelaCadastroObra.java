package biblioteca.view;

import biblioteca.controller.BibliotecaController;
import biblioteca.model.Artigo;
import biblioteca.model.Livro;
import biblioteca.model.Obra;
import biblioteca.model.Revista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastroObra extends JFrame {
    private BibliotecaController controller;
    private JComboBox<String> comboBoxTipoObra;
    private JPanel panelCamposEspecificos;
    private JTextField textFieldCodigo;
    private JTextField textFieldTitulo;
    private JTextField textFieldAutor;
    private JTextField textFieldAno;

    public TelaCadastroObra() {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Cadastro de Obra");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTipo.add(new JLabel("Tipo de Obra:"));
        comboBoxTipoObra = new JComboBox<>(new String[]{"Livro", "Revista", "Artigo"});
        panelTipo.add(comboBoxTipoObra);

        JPanel panelCamposComuns = new JPanel(new GridLayout(4, 2, 5, 5));
        panelCamposComuns.add(new JLabel("Código:"));
        textFieldCodigo = new JTextField();
        panelCamposComuns.add(textFieldCodigo);

        panelCamposComuns.add(new JLabel("Título:"));
        textFieldTitulo = new JTextField();
        panelCamposComuns.add(textFieldTitulo);

        panelCamposComuns.add(new JLabel("Autor:"));
        textFieldAutor = new JTextField();
        panelCamposComuns.add(textFieldAutor);

        panelCamposComuns.add(new JLabel("Ano de Publicação:"));
        textFieldAno = new JTextField();
        panelCamposComuns.add(textFieldAno);

        // Painel de campos específicos (dinâmico)
        panelCamposEspecificos = new JPanel(new GridLayout(2, 2, 5, 5));
        atualizarCamposEspecificos();

        // Botão de cadastro
        JButton buttonCadastrar = new JButton("Cadastrar");
        buttonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarObra();
            }
        });

        // Listener para mudança de tipo
        comboBoxTipoObra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarCamposEspecificos();
            }
        });

        // Adiciona componentes ao painel principal
        panelPrincipal.add(panelTipo, BorderLayout.NORTH);

        JPanel panelCampos = new JPanel(new BorderLayout(5, 5));
        panelCampos.add(panelCamposComuns, BorderLayout.NORTH);
        panelCampos.add(panelCamposEspecificos, BorderLayout.CENTER);

        panelPrincipal.add(panelCampos, BorderLayout.CENTER);
        panelPrincipal.add(buttonCadastrar, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void atualizarCamposEspecificos() {
        panelCamposEspecificos.removeAll();

        String tipo = (String) comboBoxTipoObra.getSelectedItem();

        switch (tipo) {
            case "Livro":
                panelCamposEspecificos.add(new JLabel("ISBN:"));
                panelCamposEspecificos.add(new JTextField());
                panelCamposEspecificos.add(new JLabel("Edição:"));
                panelCamposEspecificos.add(new JTextField());
                break;
            case "Revista":
                panelCamposEspecificos.add(new JLabel("ISSN:"));
                panelCamposEspecificos.add(new JTextField());
                panelCamposEspecificos.add(new JLabel("Número:"));
                panelCamposEspecificos.add(new JTextField());
                break;
            case "Artigo":
                panelCamposEspecificos.add(new JLabel("DOI:"));
                panelCamposEspecificos.add(new JTextField());
                panelCamposEspecificos.add(new JLabel("Periódico:"));
                panelCamposEspecificos.add(new JTextField());
                break;
        }

        panelCamposEspecificos.revalidate();
        panelCamposEspecificos.repaint();
    }

    private void cadastrarObra() {
        try {
            String codigo = textFieldCodigo.getText();
            String titulo = textFieldTitulo.getText();
            String autor = textFieldAutor.getText();
            int ano = Integer.parseInt(textFieldAno.getText());

            String tipo = (String) comboBoxTipoObra.getSelectedItem();
            Obra obra = null;

            switch (tipo) {
                case "Livro":
                    String isbn = ((JTextField) panelCamposEspecificos.getComponent(1)).getText();
                    int edicao = Integer.parseInt(((JTextField) panelCamposEspecificos.getComponent(3)).getText());
                    obra = new Livro(codigo, titulo, autor, ano, isbn, edicao);
                    break;
                case "Revista":
                    String issn = ((JTextField) panelCamposEspecificos.getComponent(1)).getText();
                    int numero = Integer.parseInt(((JTextField) panelCamposEspecificos.getComponent(3)).getText());
                    obra = new Revista(codigo, titulo, autor, ano, issn, numero);
                    break;
                case "Artigo":
                    String doi = ((JTextField) panelCamposEspecificos.getComponent(1)).getText();
                    String periodico = ((JTextField) panelCamposEspecificos.getComponent(3)).getText();
                    obra = new Artigo(codigo, titulo, autor, ano, doi, periodico);
                    break;
            }

            if (obra != null) {
                controller.cadastrarObra(obra);
                JOptionPane.showMessageDialog(this, "Obra cadastrada com sucesso!");
                dispose();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro: Verifique os campos numéricos (ano, edição, etc.)", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar obra: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}