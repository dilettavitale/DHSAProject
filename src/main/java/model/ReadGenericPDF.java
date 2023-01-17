package model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * @author danto
 */
public class ReadGenericPDF {

    File file;

    public ReadGenericPDF(File file) {
        this.file = file;
    }

    /**
     *
     * @param file
     * @return Elaborated Text
     * @throws java.io.IOException
     */
    public String extractText(File file) throws IOException {
        String totaltext = null;
        try (PDDocument document = PDDocument.load(file)) {
            AccessPermission ap = document.getCurrentAccessPermission();
            if (!ap.canExtractContent()) {
                throw new IOException("You do not have permission to extract text");
            }

            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            for (int p = 1; p <= document.getNumberOfPages(); ++p) {
                // Set the page interval to extract. If you don't, then all pages would be extracted.
                stripper.setStartPage(p);
                stripper.setEndPage(p);

                // let the magic happen
                String text = stripper.getText(document);
                totaltext += text;
                String textLenghtString = String.format("Text lenght of page %d:", p);
                System.out.println(textLenghtString);
                System.out.println(text.length() + "\n");
                // do some nice output with a header
                String pageStr = String.format("page %d:", p);
                System.out.println(pageStr);
                for (int i = 0; i < pageStr.length(); ++i) {
                    System.out.print("-");
                }
                System.out.println();
                System.out.println(text.trim());
                System.out.println();
            }
            System.out.println("\n\n----------- Information extracted from the text -----------\n\n");
            extractGeneralInformation(totaltext);
        }
        return totaltext;
    }

    public void extractGeneralInformation(String text) {
        // Read Pratictionar Name and Patient Identifier

        Pattern pattern = Pattern.compile("Medico :\\s*(\\w+)\\s+(\\w+)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String nomeMedico = matcher.group(1);        //309341008
            String cognomeMedico = matcher.group(2);
            System.out.println("Nome e cognome del medico: " + nomeMedico + " " + cognomeMedico);
        }

        pattern = Pattern.compile("NOME DEL MEDICO:\\s*(\\w+)\\s+(\\w+)");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String nomeMedico = matcher.group(1);
            String cognomeMedico = matcher.group(2);
            System.out.println("Nome e cognome del medico: " + nomeMedico + " " + cognomeMedico);
        }
    }
}