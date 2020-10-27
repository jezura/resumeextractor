package jobportal.utils;

import jobportal.models.cv_support.CzechName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CVExtractor {
    private String extractedText;

    public String getCvTextData(File file, String fileName) {
        if (fileName.endsWith(".pdf")) {
            try {
                PDDocument document = PDDocument.load(file);
                document.getClass();

                if (!document.isEncrypted()) {
                    PDFTextStripper getData = new PDFTextStripper();
                    extractedText = getData.getText(document);
                } else {
                    extractedText = "error - pdf encrypted";
                }

                document.close();
            } catch (FileNotFoundException e) {
                extractedText = "error - file not found";
                System.out.println("Error - file not found");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ((fileName.endsWith(".docx")) || (fileName.endsWith(".doc"))) {
            try {
                XWPFDocument docx = new XWPFDocument(OPCPackage.openOrCreate(file));
                XWPFWordExtractor wordExtractor = new XWPFWordExtractor(docx);
                extractedText = wordExtractor.getText();
            } catch (FileNotFoundException e) {
                extractedText = "error - file not found";
                System.out.println("Error - file not found");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return extractedText;
    }

    public String extractEmail (String extractedText) {
        String regexEmail = "(\\s?[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-z]+\\s?)";
        Pattern pattern = Pattern.compile(regexEmail);
        Matcher matcher = pattern.matcher(extractedText);

        if (matcher.find())
        {
            return matcher.group();
        } else {
            return null;
        }
    }

    public String extractFirstName (String extractedText, Collection<CzechName> czechNames) {
        for (CzechName czechName : czechNames) {
            String regexName = "\\s?(" + czechName.getName() + "\\s)";
            Pattern pattern = Pattern.compile(regexName);
            Matcher matcher = pattern.matcher(extractedText);

            if (matcher.find())
            {
                String firstName = matcher.group();
                firstName = firstName.replaceAll("\\s+","");
                return firstName;
            }
        }

        return null;
    }

    public String extractLastName (String extractedText, String firstName) {
        String regexLastName = "(" + firstName + "\\s[A-Z]{1}[a-záščěřžýíé'´]{2,20})";
        Pattern pattern = Pattern.compile(regexLastName);
        Matcher matcher = pattern.matcher(extractedText);

        if (matcher.find())
        {
            String lastName = matcher.group();
            String[] splitedLastName = lastName.split("\\s",2);
            return splitedLastName[1];
        }

        return null;
    }
}