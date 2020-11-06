package jobportal.utils;

import jobportal.models.cv_support.CzechName;
import jobportal.models.cv_support.MaxEdu;
import jobportal.models.cv_support.Title;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.tomcat.jni.Local;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
            return matcher.group().replaceAll("\\s+", "");
        } else {
            return null;
        }
    }

    public String extractMobile (String extractedText) {
        String regexMobile = "(\\s?(\\+\\s?420)?\\s? ?[4-9]{1}[0-9]{2}\\s? ?[0-9]{3}\\s? ?[0-9]{3}\\s?)";
        Pattern pattern = Pattern.compile(regexMobile);
        Matcher matcher = pattern.matcher(extractedText);

        if (matcher.find())
        {
            return matcher.group().replaceAll("\\s+", "").replaceAll(" ","");
        } else {
            return null;
        }
    }

    public List<Title> extractTitle (String extractedText, Collection<Title> titles) {
        List<Title> titlesList = new ArrayList<Title>();
        for (Title title : titles) {
            String regexTitle = "\\s?(" + title.getTitleVariant() + "\\.\\s)";
            Pattern pattern = Pattern.compile(regexTitle);
            Matcher matcher = pattern.matcher(extractedText);

            if (matcher.find())
            {
                if(!titlesList.contains(title)){
                    titlesList.add(title);
                }
            }
        }
        return titlesList;
    }


    public CzechName extractFirstName (String extractedText, Collection<CzechName> czechNames) {
        for (CzechName czechName : czechNames) {
            String regexName = "\\s?(" + czechName.getName() + "\\s|" + czechName.getName().toUpperCase() + "\\s)";
            Pattern pattern = Pattern.compile(regexName);
            Matcher matcher = pattern.matcher(extractedText);

            if (matcher.find())
            {
                return czechName;
            }
        }

        return null;
    }


    public String extractLastName (String extractedText, String firstName) {
        String regexLastName = "(((" + firstName + "\\s)[A-Z]{1}[a-záščěřžýíé'´]{2,20})" + "|((" + firstName.toUpperCase() + "\\s)[A-ZÁŠČĚŘŽÝÍÉ'´]{2,20}\\s))";
        Pattern pattern = Pattern.compile(regexLastName);
        Matcher matcher = pattern.matcher(extractedText);

        if (matcher.find())
        {
            String lastName = matcher.group();
            String[] splitedLastName = lastName.split("\\s",2);
            return splitedLastName[1].replaceAll("\\s+", "");
        } else {
            // try to find last name before first name (in different order) - e.g. "Novak Jan"
            regexLastName = "(([A-Z]{1}[a-záščěřžýíé'´]{2,20}\\s" + firstName + ")|([A-ZÁŠČĚŘŽÝÍÉ'´]{2,20}\\s" + firstName.toUpperCase() + "))";
            pattern = Pattern.compile(regexLastName);
            matcher = pattern.matcher(extractedText);
            if (matcher.find())
            {
                String lastName = matcher.group();
                String[] splitedLastName = lastName.split("\\s",2);
                return splitedLastName[0].replaceAll("\\s+", "");
            }else{
                return null;
            }
        }
    }

    /*public String extractBirthDate (String extractedText) {
        String regexBirthDate = "([0-2][1-9]|[1-3][0-9]|[1-9]|30|31)\\.\\s?(0[1-9]|[1-9]|1[0-2])\\.\\s?(19[5-9][0-9]|200[0-3])";
        Pattern pattern = Pattern.compile(regexBirthDate);
        Matcher matcher = pattern.matcher(extractedText);

        if (matcher.find())
        {
            return matcher.group().replaceAll("\\s+", "");
        } else {
            return null;
        }
    }*/

    /*public LocalDate extractBirthDate (String extractedText) {
        String regexBirthDate = "([0-2][1-9]|[1-3][0-9]|[1-9]|30|31)\\.\\s?(0[1-9]|[1-9]|1[0-2])\\.\\s?(19[5-9][0-9]|200[0-3])";
        Pattern pattern = Pattern.compile(regexBirthDate);
        Matcher matcher = pattern.matcher(extractedText);

        if (matcher.find())
        {
            LocalDate birthDate = LocalDate.of(Integer.valueOf(matcher.group(3)),Integer.valueOf(matcher.group(2)),Integer.valueOf(matcher.group(1)));
            return  birthDate;

        } else {
            return null;
        }
    }*/

    public LocalDate extractBirthDate (String extractedText) {
        String regexBirthDate = "([Dd]atum narozen[ií]|[Nn]arozen[a]?)\\s?(:|-|–|_)?\\s?([0-2][1-9]|[1-3][0-9]|[1-9]|30|31)\\.\\s?(0[1-9]|[1-9]|1[0-2])\\.\\s?(19[5-9][0-9]|200[0-3])";
        Pattern pattern = Pattern.compile(regexBirthDate);
        Matcher matcher = pattern.matcher(extractedText);

        if (matcher.find()){
            return LocalDate.of(Integer.valueOf(matcher.group(5)),Integer.valueOf(matcher.group(4)),Integer.valueOf(matcher.group(3)));

        } else {
            regexBirthDate = "([0-2][1-9]|[1-3][0-9]|[1-9]|30|31)\\.\\s?(0[1-9]|[1-9]|1[0-2])\\.\\s?(19[5-9][0-9]|200[0-3])";
            pattern = Pattern.compile(regexBirthDate);
            matcher = pattern.matcher(extractedText);

            if (matcher.find()) {
                return LocalDate.of(Integer.valueOf(matcher.group(3)),Integer.valueOf(matcher.group(2)),Integer.valueOf(matcher.group(1)));

            } else {
                // If there is no proper date format found, we will try to found only birth year,
                // if it is mentioned here.
                regexBirthDate = "([Rr]ok narozen[ií]|[Nn]arozen[aá]?(\\s[Rr]oku)?)\\s?(:|-|–|_)?\\s?(19[5-9][0-9]|200[0-3])";
                pattern = Pattern.compile(regexBirthDate);
                matcher = pattern.matcher(extractedText);

                if (matcher.find()) {
                    return LocalDate.of(Integer.valueOf(matcher.group(4)) - 100,1,1);

                } else {
                    // ESTIMATION
                    // If there is no found any birth date or birth year - then there is the last less accurate
                    // estimation method based on finding all valid years listed in the CV, choosing the lowest.
                    // Then it is found out whether the record of the primary school is mentioned in the curriculum vitae.
                    // If so, 6 years will be deducted from the lowest year found.
                    // If not, the high school calculation is chosen and the value 15 is subtracted.
                    // This gives the final estimate of the year of birth and thus the age of the respondent.
                    regexBirthDate = "(19[6-9][0-9]|20[0-1][0-9])";
                    pattern = Pattern.compile(regexBirthDate);
                    matcher = pattern.matcher(extractedText);

                    List<Integer> years = new ArrayList<>();
                    while (matcher.find()) {
                        years.add(Integer.valueOf(matcher.group()));
                    }

                    int minYear = LocalDate.now().getYear();
                    for (int year:years) {
                        if(year <= minYear) {
                            minYear = year;
                        }
                    }
                    String regexPrimarySchool = "([Zz][aá]kladn[ií])\\s([SsŠš]kola)|([Zz][SsŠš](\\s|:|-))";
                    pattern = Pattern.compile(regexPrimarySchool);
                    matcher = pattern.matcher(extractedText);

                    if(matcher.find()) {
                        System.out.println("Odhad je - zš. Takže: " + minYear + " - 6.");
                        return LocalDate.of(minYear - 6 - 100,1,1);
                    } else {
                        System.out.println("Odhad je - SŠ. Takže: " + minYear + " - 15.");
                        return LocalDate.of(minYear - 15 - 100,1,1);
                    }
                }
            }
        }
    }

    public MaxEdu extractMaxEducationAndGeneralField (String extractedText, Collection<Title> titles) {
        String maxEduLvl = "";
        String eduGeneralField = "";

        if(!titles.isEmpty()) {
            for (Title title : titles) {
                if (title.getDegree().equals("Vysokoskolske_bakalarske")) {
                    maxEduLvl = "Vysokoskolske_bakalarske";
                    if(title.getSchoolType() != null) {
                        eduGeneralField = title.getSchoolType();
                    }
                }
            }

            for (Title title : titles) {
                if (title.getDegree().equals("Vysokoskolske_magisterske")) {
                    maxEduLvl = "Vysokoskolske_magisterske";
                    if(title.getSchoolType() != null) {
                        eduGeneralField = title.getSchoolType();
                    }
                }
            }

            for (Title title : titles) {
                if (title.getDegree().equals("Vysokoskolske_doktorske")) {
                    maxEduLvl = "Vysokoskolske_doktorske";
                    if(title.getSchoolType() != null) {
                        eduGeneralField = title.getSchoolType();
                    }
                }
            }

            if(!eduGeneralField.isEmpty()) {
                return new MaxEdu(maxEduLvl, eduGeneralField);
            } else {
                return new MaxEdu(maxEduLvl, eduGeneralField);
            }
        }

        return null;
    }
}

    //([0-2][0-9]|30|31)\.\s?([0-1][0-9])\.\s?(19[5-9][0-9]|200[0-3])
    //([0-2][0-9]|[1-9]|30|31)\.\s?([0-1]?[0-9])\.\s?(19[5-9][0-9]|200[0-3])
    //([0-2][1-9]|[1-3][0-9]|[1-9]|30|31)\.\s?(0[1-9]|[1-9]|1[0-2])\.\s?(19[5-9][0-9]|200[0-3])