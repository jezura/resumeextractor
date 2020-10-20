package jobportal.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.core.io.ClassPathResource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PdfExtractor {
    private String extractedText;

    public String getPdfTextData(File file) {

        try
        {
            //File file = new ClassPathResource("cv.pdf").getFile();
            PDDocument document = PDDocument.load(file);
            document.getClass();

            if (!document.isEncrypted())
            {
                PDFTextStripper getData = new PDFTextStripper();
                extractedText = getData.getText(document);
            }
            else
            {
                extractedText = "error - pdf encrypted";
            }

            document.close();
        }
        catch (FileNotFoundException e)
        {
            extractedText = "error - file not found";
            System.out.println("Error - file not found");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return extractedText;
    }
}