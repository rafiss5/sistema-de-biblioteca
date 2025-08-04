package view;

import controller.RelatorioController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class TelaRelatorios extends JFrame {
    // Constantes para os tipos de relatório
    private static final String REL_EMP_MES = "Empréstimos por Mês";
    private static final String REL_OBRAS_MAIS_EMP = "Obras Mais Emprestadas";
    private static final String REL_USUARIOS_ATRASO = "Usuários com Mais Atrasos";

    // Componentes da interface
    private JComboBox<String> cbMeses;
    private JSpinner spinnerAno;
    private JComboBox<String> cbTipoRelatorio;
    private JButton btnGerarRelatorio;
    private JButton btnSelecionarLocal;
    private JLabel lblMes;
    private JLabel lblAno;

    // Controller
    private final RelatorioController controller;

    // Caminho para salvar o relatório
    private String caminhoArquivo;

    public TelaRelatorios(RelatorioController relatorioController) {
        this.controller = new RelatorioController();
        initComponents();
        configurarJanela();
    }

    private void initComponents() {
        // Configuração do painel principal
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tipo de Relatório
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Tipo de Relatório:"), gbc);

        gbc.gridx = 1;
        cbTipoRelatorio = new JComboBox<>(new String[]{
                REL_EMP_MES,
                REL_OBRAS_MAIS_EMP,
                REL_USUARIOS_ATRASO
        });
        cbTipoRelatorio.addActionListener(this::atualizarCamposVisiveis);
        panel.add(cbTipoRelatorio, gbc);

        // Mês (visível apenas para relatório por mês)
        gbc.gridx = 0;
        gbc.gridy = 1;
        lblMes = new JLabel("Mês:");
        panel.add(lblMes, gbc);

        gbc.gridx = 1;
        cbMeses = new JComboBox<>(new String[]{
                "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        });
        panel.add(cbMeses, gbc);

        // Ano (visível apenas para relatório por mês)
        gbc.gridx = 0;
        gbc.gridy = 2;
        lblAno = new JLabel("Ano:");
        panel.add(lblAno, gbc);

        gbc.gridx = 1;
        spinnerAno = new JSpinner(new SpinnerNumberModel(2023, 2000, 2100, 1));
        panel.add(spinnerAno, gbc);

        // Botão para selecionar local de salvamento
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        btnSelecionarLocal = new JButton("Selecionar Local para Salvar");
        btnSelecionarLocal.addActionListener(this::selecionarLocalSalvamento);
        panel.add(btnSelecionarLocal, gbc);

        // Botão para gerar relatório
        gbc.gridy = 4;
        btnGerarRelatorio = new JButton("Gerar Relatório");
        btnGerarRelatorio.addActionListener(this::gerarRelatorio);
        panel.add(btnGerarRelatorio, gbc);

        // Atualiza visibilidade dos campos inicialmente
        atualizarCamposVisiveis(null);

        this.add(panel);
    }

    private void configurarJanela() {
        this.setTitle("Geração de Relatórios");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    private void atualizarCamposVisiveis(ActionEvent e) {
        String tipoRelatorio = (String) cbTipoRelatorio.getSelectedItem();
        boolean mostrarCamposMes = tipoRelatorio.equals(REL_EMP_MES);

        // Atualiza visibilidade dos componentes
        lblMes.setVisible(mostrarCamposMes);
        lblAno.setVisible(mostrarCamposMes);
        cbMeses.setVisible(mostrarCamposMes);
        spinnerAno.setVisible(mostrarCamposMes);
    }

    private void selecionarLocalSalvamento(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Relatório");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            caminhoArquivo = fileChooser.getSelectedFile().getAbsolutePath();
            JOptionPane.showMessageDialog(this,
                    "Local selecionado: " + caminhoArquivo,
                    "Local de Salvamento",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void gerarRelatorio(ActionEvent e) {
        if (caminhoArquivo == null || caminhoArquivo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um local para salvar o relatório primeiro!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cria o diretório se não existir
        File diretorio = new File(caminhoArquivo);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        String tipoRelatorio = (String) cbTipoRelatorio.getSelectedItem();

        try {
            switch (tipoRelatorio) {
                case REL_EMP_MES:
                    int mesSelecionado = cbMeses.getSelectedIndex() + 1;
                    int anoSelecionado = (int) spinnerAno.getValue();
                    controller.gerarRelatorioEmprestimosMes(
                            mesSelecionado, anoSelecionado, caminhoArquivo);
                    break;

                    /*
                case REL_OBRAS_MAIS_EMP:
                    controller.gerarRelatorioObrasMaisEmprestadas(caminhoArquivo);
                    break;

                     */

                case REL_USUARIOS_ATRASO:
                    controller.gerarRelatorioUsuariosComMaisAtrasos(caminhoArquivo);
                    break;
            }

            JOptionPane.showMessageDialog(this,
                    "Relatório gerado com sucesso em:\n" + caminhoArquivo,
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao gerar relatório: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}