package jobportal.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class WordExtractor {
    private String extractedText;

    public String getWordTextData() {

        try
        {
            File file = new ClassPathResource("cv.docx").getFile();
            XWPFDocument docx = new XWPFDocument(OPCPackage.openOrCreate(file));
            XWPFWordExtractor wordExtractor = new XWPFWordExtractor(docx);
            extractedText = wordExtractor.getText();
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