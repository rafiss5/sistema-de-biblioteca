package biblioteca.controller;

public class RelatorioController {

    public void gerarRelatorioEmprestimosMes(int mes, int ano, String caminho) {
        try {

            String conteudo = "Relatório de empréstimos - Mês: " + mes + ", Ano: " + ano;
            String caminhoCompleto = caminho + "/emprestimos_mes_" + mes + "_" + ano + ".pdf";

            System.out.println("Relatório gerado em: " + caminhoCompleto);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    public void gerarRelatorioObrasMaisEmprestadas(String caminho) {
        try {
            // Lógica para gerar o relatório
            String caminhoCompleto = caminho + "/obras_mais_emprestadas.pdf";
            System.out.println("Relatório gerado em: " + caminhoCompleto);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    public void gerarRelatorioUsuariosComMaisAtrasos(String caminho) {
        try {
            String caminhoCompleto = caminho + "/usuarios_com_atrasos.pdf";
            System.out.println("Relatório gerado em: " + caminhoCompleto);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório: " + e.getMessage());
        }
    }
}