package resumeextractor.utils;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import resumeextractor.models.cv_support.CzechName;
import resumeextractor.models.cv_support.MaxEduLvl;
import resumeextractor.models.cv_support.MaxEducation;
import resumeextractor.models.cv_support.Title;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;


public class CVExtractor {
    private String extractedText;
    private int aroundArea = 70;

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
        String regexEmail = "(\\s?[a-z0-9._-]+@[a-z0-9.-]+\\.[a-z]{2,5}\\s?)";
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
        String regexTitle;
        Boolean unique;
        List<Title> titlesList = new ArrayList<Title>();
        for (Title title : titles) {
            if(title.getOfficialVersion().equals("Dr")) {
                regexTitle = "(\\s|\\n)(" + title.getTitleVariant() + "\\.\\s)";
            }else{
                regexTitle = "\\s?(" + title.getTitleVariant() + "\\.\\s)";
            }
            Pattern pattern = Pattern.compile(regexTitle);
            Matcher matcher = pattern.matcher(extractedText);

            if (matcher.find())
            {
                // checking if extracted title is not already stored in titlesList
                unique = true;
                for (Title extractedTitle: titlesList) {
                    if(extractedTitle.getOfficialVersion().equals(title.getOfficialVersion())) {
                        unique = false;
                    }
                }

                if(unique) {
                    titlesList.add(title);
                }
            }
        }
        return titlesList;
    }


    public CzechName extractFirstName (String extractedText, Collection<CzechName> czechNames, int sizeOfStartArea) {

        for(int counter = 0; counter < 10; counter++) {
            String startSubString = extractedText.substring(0, sizeOfStartArea);
            // try to find first name only on beginning of CV text (normally it is on beginning).
            for (CzechName czechName : czechNames) {
                String regexName = "\\s?(" + czechName.getName() + "\\s|" + czechName.getName().toUpperCase() + "\\s)";
                Pattern pattern = Pattern.compile(regexName);
                Matcher matcher = pattern.matcher(startSubString);

                if (matcher.find())
                {
                    return czechName;
                }
            }
            sizeOfStartArea += 20;
        }

        // try to find first name in whole CV text, if previous method failed
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
        String regexLastName = "(((" + firstName + "\\s)(D[´'´])?[A-Z][a-záščěřžýíé'´]{2,20})" + "|((" + firstName.toUpperCase() + "\\s)[A-ZÁŠČĚŘŽÝÍÉ'´]{2,20}\\s))";
        Pattern pattern = Pattern.compile(regexLastName);
        Matcher matcher = pattern.matcher(extractedText);

        if (matcher.find())
        {
            String lastName = matcher.group();
            String[] splitedLastName = lastName.split("(\\s)",2);
            return splitedLastName[1].replaceAll("\\s+", "");
        } else {
            // try to find last name before first name (in different order) - e.g. "Novak Jan"
            regexLastName = "(((D[´'´])?[A-Z][a-záščěřžýíé'´]{2,20}\\s" + firstName + ")|([A-ZÁŠČĚŘŽÝÍÉ'´]{2,20}\\s" + firstName.toUpperCase() + "))";
            pattern = Pattern.compile(regexLastName);
            matcher = pattern.matcher(extractedText);
            if (matcher.find())
            {
                String lastName = matcher.group();
                String[] splitedLastName = lastName.split("\\s",2);
                return splitedLastName[0].replaceAll("\\s+", "");
            }else{
                // try to find last name at second paragraph "\\n"
                regexLastName = "(((" + firstName + "\\s*\\n)(D[´'´])?[A-Z][a-záščěřžýíé'´]{2,20})" + "|((" + firstName.toUpperCase() + "\\s*\\n)[A-ZÁŠČĚŘŽÝÍÉ'´]{2,20}\\s))";
                pattern = Pattern.compile(regexLastName);
                matcher = pattern.matcher(extractedText);

                if (matcher.find())
                {
                    String lastName = matcher.group();
                    String[] splitedLastName = lastName.split("(\\s|\\n)",2);
                    return splitedLastName[1].replaceAll("\\s+", "");
                } else {
                    return null;
                }
            }
        }
    }


    public LocalDate extractBirthDate (String extractedText) {
        String regexBirthDate = "([Dd]atum narozen[ií]|[Nn]arozen[a]?)\\s?(:|-|–|_)?\\s?([0-2][1-9]|[1-3][0-9]|[1-9]|30|31)(\\.\\s?|/|_)(0[1-9]|[1-9]|1[0-2])(\\.\\s?|/|_)(19[5-9][0-9]|200[0-3])";
        Pattern pattern = Pattern.compile(regexBirthDate);
        Matcher matcher = pattern.matcher(extractedText);

        if (matcher.find()){
            return LocalDate.of(Integer.valueOf(matcher.group(7)),Integer.valueOf(matcher.group(5)),Integer.valueOf(matcher.group(3)));

        } else {
            regexBirthDate = "([0-2][1-9]|[1-3][0-9]|[1-9]|30|31)(\\.\\s?|/|_)(0[1-9]|[1-9]|1[0-2])(\\.\\s?|/|_)(19[5-9][0-9]|200[0-3])";
            pattern = Pattern.compile(regexBirthDate);
            matcher = pattern.matcher(extractedText);

            if (matcher.find()) {
                return LocalDate.of(Integer.valueOf(matcher.group(5)),Integer.valueOf(matcher.group(3)),Integer.valueOf(matcher.group(1)));

            } else {
                // If there is no proper date format found, we will try to found only birth year,
                // if it is mentioned here.
                regexBirthDate = "([Rr]ok narozen[ií]|[Nn]arozen[aá]?(\\s[Rr]oku)?|[Nn]arození)\\s?(:|-|–|_)?\\s?(19[5-9][0-9]|200[0-3])";
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

    public MaxEducation extractMaxEducationAndGeneralEduField (String extractedText, Collection<Title> titles) {
        MaxEduLvl maxEduLvl = new MaxEduLvl();
        MaxEducation maxEducation = new MaxEducation();

        if(!titles.isEmpty()) {
            for (Title title : titles) {
                if (title.getDegree().equals("Vysokoskolske_bakalarske")) {
                    maxEduLvl.setMaxEduLvlName(title.getDegree());
                    if(title.getTitleEduField() != null) {
                        maxEducation.setGeneralEduField(title.getTitleEduField());
                    }
                }
            }

            for (Title title : titles) {
                if (title.getDegree().equals("Vysokoskolske_magisterske")) {
                    maxEduLvl.setMaxEduLvlName(title.getDegree());
                    if(title.getTitleEduField() != null) {
                        maxEducation.setGeneralEduField(title.getTitleEduField());
                    }
                }
            }

            for (Title title : titles) {
                if (title.getDegree().equals("Vysokoskolske_doktorske")) {
                    maxEduLvl.setMaxEduLvlName(title.getDegree());
                    if(title.getTitleEduField() != null) {
                        maxEducation.setGeneralEduField(title.getTitleEduField());
                    }
                }
            }

            maxEducation.setMaxEduLvl(maxEduLvl);

            if(maxEducation.getGeneralEduField() != null) {
                return maxEducation;
            } else {
                maxEduLvl.findAreaIndexesForVSLevel(extractedText, aroundArea);
                maxEducation.setMaxEduLvl(maxEduLvl);
                if (maxEducation.findFieldForVSLevel(extractedText, true, false)) {
                    return maxEducation;
                } else if (maxEducation.findFieldForVSLevel(extractedText, false, true)) {
                    return maxEducation;
                } else if (maxEducation.findFieldForVSLevel(extractedText, false, false)) {
                    return maxEducation;
                } else {
                    return null;
                }
            }
        } else {
            if(maxEducation.findEduSectionStartIndex(extractedText)) {
                boolean success = maxEduLvl.findMaxEduLvl(extractedText, maxEducation.getEduSectionStartIndex(), aroundArea);
                if(!success) {
                    maxEduLvl.findMaxEduLvl(extractedText, 0, aroundArea);
                }
            }else{
                maxEduLvl.findMaxEduLvl(extractedText, 0, aroundArea);
            }
            maxEducation.setMaxEduLvl(maxEduLvl);
            switch (maxEduLvl.getMaxEduLvlName()) {
                case "Vysokoskolske_doktorske": case "Vysokoskolske_magisterske": case "Vysokoskolske_bakalarske":
                    if (maxEducation.findFieldForVSLevel(extractedText, true, false)) {
                        return maxEducation;
                    } else if (maxEducation.findFieldForVSLevel(extractedText, false, true)) {
                        return maxEducation;
                    } else if (maxEducation.findFieldForVSLevel(extractedText, false, false)) {
                        return maxEducation;
                    } else {
                        return null;
                    }
                case "Vyssi_odborne": case "Stredoskolske_s_maturitou":
                    if (maxEducation.findFieldForVOSSSMatLevel(extractedText, true, false)) {
                        return maxEducation;
                    } else if (maxEducation.findFieldForVOSSSMatLevel(extractedText, false, true)) {
                        return maxEducation;
                    } else if (maxEducation.findFieldForVOSSSMatLevel(extractedText, false, false)) {
                        return maxEducation;
                    } else {
                        return null;
                    }
                case "Vyuceni_nebo_Stredoskolske_bez_maturity":
                    if (maxEducation.findFieldForSSLevel(extractedText, true, false)) {
                        return maxEducation;
                    } else if (maxEducation.findFieldForSSLevel(extractedText, false, true)) {
                        return maxEducation;
                    } else if (maxEducation.findFieldForSSLevel(extractedText, false, false)) {
                        return maxEducation;
                    } else {
                        return null;
                    }
                case "Zakladni":
                    maxEducation.setGeneralEduField("Zakladni_skola");
                    return maxEducation;
            }
        }

        return null;
    }

    /*public void getPredictions() throws IOException {
       URL url = new URL("https://fieldpredictor.herokuapp.com/prediction");

       // Get the input stream through URL Connection
       URLConnection connection = url.openConnection();
       InputStream is = connection.getInputStream();

       BufferedReader br = new BufferedReader(new InputStreamReader(is));

       String line = null;
       while ((line = br.readLine()) != null) {
           System.out.println(line);
       }
    }*/
}