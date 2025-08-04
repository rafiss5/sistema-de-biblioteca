package view;

import controller.ObraController;
import model.Artigo;
import model.Livro;
import model.Obra;
import model.Revista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaCadastroObra extends JFrame {
    private ObraController obraController;
    private JComboBox<String> comboBoxTipoObra;
    private JPanel painelCamposEspecificos;
    private JTextField textFieldCodigo;
    private JTextField textFieldTitulo;
    private JTextField textFieldAutor;
    private JTextField textFieldAno;
    private JTextField textFieldEdicaoNumeroPeriodico;

    public TelaCadastroObra(ObraController obraController) {
        this.obraController = new ObraController();
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

        // Painel de campos específicos
        painelCamposEspecificos = new JPanel(new GridLayout(1, 2, 5, 5));
        atualizarCamposEspecificos();

        // Botão de cadastro
        JButton buttonCadastrar = new JButton("Cadastrar");
        buttonCadastrar.addActionListener(this::cadastrarObra);

        // Listener para mudança de tipo
        comboBoxTipoObra.addActionListener(e -> atualizarCamposEspecificos());

        // Layout
        panelPrincipal.add(panelTipo, BorderLayout.NORTH);

        JPanel panelCampos = new JPanel(new BorderLayout(5, 5));
        panelCampos.add(panelCamposComuns, BorderLayout.NORTH);
        panelCampos.add(painelCamposEspecificos, BorderLayout.CENTER);

        panelPrincipal.add(panelCampos, BorderLayout.CENTER);
        panelPrincipal.add(buttonCadastrar, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void atualizarCamposEspecificos() {
        painelCamposEspecificos.removeAll();

        String tipo = (String) comboBoxTipoObra.getSelectedItem();
        String labelTexto = "";

        switch (tipo) {
            case "Livro":
                labelTexto = "ISBN:";
                break;
            case "Revista":
                labelTexto = "ISSN:";
                break;
            case "Artigo":
                labelTexto = "DOI:";
                break;
        }

        painelCamposEspecificos.add(new JLabel(labelTexto));
        textFieldEdicaoNumeroPeriodico = new JTextField();
        painelCamposEspecificos.add(textFieldEdicaoNumeroPeriodico);

        painelCamposEspecificos.revalidate();
        painelCamposEspecificos.repaint();
    }

    private void cadastrarObra(ActionEvent e) {
        try {
            // Obter valores dos campos
            String codigo = textFieldCodigo.getText().trim();
            String titulo = textFieldTitulo.getText().trim();
            String autor = textFieldAutor.getText().trim();
            int ano = Integer.parseInt(textFieldAno.getText().trim());
            String campoEspecifico = textFieldEdicaoNumeroPeriodico.getText().trim();

            // Criar obra conforme o tipo selecionado
            String tipo = (String) comboBoxTipoObra.getSelectedItem();
            Obra obra;

            switch (tipo) {
                case "Livro":
                    obra = new Livro(codigo, titulo, autor, ano, campoEspecifico, 1); // Edição padrão 1
                    break;
                case "Revista":
                    obra = new Revista(codigo, titulo, autor, ano, campoEspecifico, 1); // Número padrão 1
                    break;
                case "Artigo":
                    obra = new Artigo(codigo, titulo, autor, ano, campoEspecifico, "Periódico");
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de obra inválido");
            }

            // Cadastrar a obra
            obraController.cadastrarObra(obra);

            JOptionPane.showMessageDialog(this,
                    "Obra cadastrada com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ano de publicação deve ser um número válido",
                    "Erro de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao cadastrar obra: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
