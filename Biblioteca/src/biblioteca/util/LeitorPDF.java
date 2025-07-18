package biblioteca.util;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import java.io.IOException;

public class LeitorPDF {

    public static String lerConteudoPDF(String caminhoArquivo) throws IOException {
        PdfReader reader = new PdfReader(caminhoArquivo);
        StringBuilder conteudo = new StringBuilder();
        PdfTextExtractor extractor = new PdfTextExtractor(reader);

        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            conteudo.append(extractor.getTextFromPage(i));
        }

        reader.close();
        return conteudo.toString();
    }

    public static boolean verificarConteudoPDF(String caminhoArquivo, String textoProcurado) throws IOException {
        String conteudo = lerConteudoPDF(caminhoArquivo);
        return conteudo.contains(textoProcurado);
    }
}