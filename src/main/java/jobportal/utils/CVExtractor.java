package jobportal.utils;

import jobportal.models.cv_support.CzechName;
import jobportal.models.cv_support.MaxEduLvl;
import jobportal.models.cv_support.MaxEducation;
import jobportal.models.cv_support.Title;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CVExtractor {
    private String extractedText;
    private int aroundArea = 50;

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
        String regexEmail = "(\\s?[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+\\s?)";
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
                if(maxEducation.findFieldForVSLevel(extractedText, true)) {
                    return maxEducation;
                } else {
                    if(maxEducation.findFieldForVSLevel(extractedText, false)) {
                        return maxEducation;
                    } else {
                        return null;
                    }
                }
            }
        } else {
            maxEduLvl.findMaxEduLvl(extractedText);
            maxEducation.setMaxEduLvl(maxEduLvl);
            switch (maxEduLvl.getMaxEduLvlName()) {
                case "Vysokoskolske_doktorske": case "Vysokoskolske_magisterske": case "Vysokoskolske_bakalarske":
                    if(maxEducation.findFieldForVSLevel(extractedText, true)) {
                        return maxEducation;
                    } else {
                        if(maxEducation.findFieldForVSLevel(extractedText, false)) {
                            return maxEducation;
                        } else {
                            return null;
                        }
                    }
                case "Vyssi_odborne": case "Stredoskolske_s_maturitou":
                    if(maxEducation.findFieldForVOSSSMatLevel(extractedText, true)) {
                        return maxEducation;
                    } else {
                        if(maxEducation.findFieldForVOSSSMatLevel(extractedText, false)) {
                            return maxEducation;
                        } else {
                            return null;
                        }
                    }
                case "Vyuceni_nebo_Stredoskolske_bez_maturity":
                    if(maxEducation.findFieldForSSLevel(extractedText, true)) {
                        return maxEducation;
                    } else {
                        if(maxEducation.findFieldForSSLevel(extractedText, false)) {
                            return maxEducation;
                        } else {
                            return null;
                        }
                    }
                case "Zakladni":
                    maxEducation.setGeneralEduField("Zakladni_skola");
                    return maxEducation;
            }
        }

        return null;
    }

   /* public MaxEduLvl findVSEduPosIndexes(MaxEduLvl maxEduLvl, String extractedText, int aroundArea) {
        String regex;
        int minStartIndex;
        int maxEndIndex;
        List<Integer> startIndexes = new ArrayList<>();
        List<Integer> endIndexes = new ArrayList<>();

        switch (maxEduLvl.getMaxEduLvlName()) {
            case "Vysokoskolske_doktorske":
                regex = "([Dd]oktorské\\n|[Dd]oktorát\\n)";
                break;
            case "Vysokoskolske_magisterske":
                regex = "([Mm]agisterské|[Mm]agisterský|[Mm]agistr(a)?" +
                        "|[Ii]nženýr|ING\\n|[Nn]avazující)";
                break;
            case "Vysokoskolske_bakalarske":
                regex = "([Bb]akalář|[Bb][Cc]\\n|[Bb]atchelor|[Uu]niver[zs]it|[Vv]ysok[áé]\\n[Šš]kol" +
                        "|[Vv]ysokoškol|[Ff]akult[aě]|UHK\\n|UPCE\\n|AMU\\n|AVU\\n|ČZU\\n|ČVUT\\n" +
                        "|JAMU\\n|JU\\n|MU\\n|MUNI\\n|MENDELU\\n|OU\\n|SU\\n|TUL\\n|UJEP\\n|UK\\n" +
                        "|UP\\n|UPa\\n|UTB\\n|VFU\\n|VŠB\\n|VŠE\\n|VŠCHT\\n|VŠPJ\\n|VŠTE\\n|UMPRUM\\n" +
                        "|VUT\\n|ZČU\\n)";
                break;
            default:
                regex = "([Bb]akalář|[Bb][Cc]\\n|[Bb]atchelor|[Uu]niver[zs]it|[Vv]ysok[áé]\\n[Šš]kol" +
                        "|[Vv]ysokoškol|[Ff]akult[aě]|UHK\\n|UPCE\\n|AMU\\n|AVU\\n|ČZU\\n|ČVUT\\n" +
                        "|JAMU\\n|JU\\n|MU\\n|MUNI\\n|MENDELU\\n|OU\\n|SU\\n|TUL\\n|UJEP\\n|UK\\n" +
                        "|UP\\n|UPa\\n|UTB\\n|VFU\\n|VŠB\\n|VŠE\\n|VŠCHT\\n|VŠPJ\\n|VŠTE\\n|UMPRUM\\n" +
                        "|VUT\\n|ZČU\\n)";
                break;

        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(extractedText);

        while (matcher.find()) {
            startIndexes.add(matcher.start());
            endIndexes.add(matcher.end());
        }

        if(startIndexes.isEmpty()) {
            maxEduLvl.setStartPosIndex(0);
            maxEduLvl.setEndPosIndex(0);
        }else{
            minStartIndex = startIndexes.get(0);
            for (int startIndex: startIndexes) {
                if(startIndex < minStartIndex) {
                    minStartIndex = startIndex;
                }
            }

            maxEndIndex = endIndexes.get(0);
            for (int endIndex: endIndexes) {
                if(endIndex > maxEndIndex) {
                    maxEndIndex = endIndex;
                }
            }

            minStartIndex = minStartIndex - aroundArea;
            if(minStartIndex <0) {
                minStartIndex = 0;
            }
            maxEndIndex = maxEndIndex + aroundArea;

            maxEduLvl.setStartPosIndex(minStartIndex);
            maxEduLvl.setEndPosIndex(maxEndIndex);

        }

        return maxEduLvl;
    }

    public String findMaxEduLvl(String extractedText) {
        // dodelat vyhledani maxEdu + pokusit se zjistit oblast nalezu start-end
        String[] maxEduLvls = {
                "Vysokoskolske_doktorske",
                "Vysokoskolske_magisterske",
                "Vysokoskolske_bakalarske",
                "Vyssi_odborne",
                "Stredoskolske_s_maturitou",
                "Vyuceni_nebo_Stredoskolske_bez_maturity",
                "Zakladni"
        };
        int startIndex;
        int endIndex;

        String regex = "([Dd]oktorské\\n|[Dd]oktorát\\n)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(extractedText);

        while (matcher.find()) {

        }


        return null;
    }

    public String findFieldForVSLevel(String extractedText, int sectionStart) {
        String[] VSFields = {
                "Informatika_a_management",
                "Elektrotechnika_a_technika",
                "Obchod_a_ekonomie",
                "Zdravotnictvi_a_medicina",
                "Pravo",
                "Stavebnictvi",
                "Zemedelstvi_a_lesnictvi",
                "Pedagogika,ucitelstvi_a_telovychova",
                "Filosofie,politologie,zurnalistika,psychologie,sociologie,historie",
                "Teologie",
                "Umeni",
                "Obrana_a_ochrana",
                "Doprava",
                "Prirodni_vedy,chemie_fyzika_matematika",
                "Lingvistika"
                };

        String regex = "([Ii]nformatik[ay]|[Ii]nformační|[Mm]anagement|[Dd]at[ao]|FIM|FIT|\\sFI|FIS|FAI)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VSFields[0];
        }

        regex = "([Ee]lektrotechnik[ay]|[Ee]lektron|[Tt]elekomunika|[Aa]utomatizac|FEL|FEKT)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VSFields[1];
        }

        regex = "([Oo]bchod|[Ff]inan|[Ee]konomi|\\sNF|FPH|ECON|FEK|FSE|FES|EKF|OPF|F[Aa]ME|PEF)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VSFields[2];
        }

        regex = "([Zz]dravotn|[Ff]armac|[Ss]estra|[Zz]ubař|[Ll]ékař|[Mm]edicín|[Vv]eterin|[Ff]yzioterapie|FVZ|\\sLF|FVL|VFU|FZS)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VSFields[3];
        }

        regex = "([Pp]ráv[ao]|[Pp]rávní|[Pp]rávnic|P[Rr]F|FPR|\\sFP|\\sPF)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VSFields[4];
        }

        regex = "([Ss]tavebn[ií]|[Aa]rchitekt|FAST|FSv)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VSFields[5];
        }

        regex = "([Zz]eměděls|[Aa]gro|[Ll]esnic|[Rr]ybářství|[Zz]ahradnic|FLD|LDF|FROV|FAPPZ|\\sZF|\\sAF)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VSFields[6];
        }

        regex = "([Pp]edagogi|[Uu]čitels|[Tt]ělových|[Tt]ělesné|[Ss]port|FTVS|FTK|FPE|P[Dd]F)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VSFields[7];
        }


        regex = "([Ff]ilosofie|[Ff]ilosofick|[Pp]olitologie|[Pp]olitologick|[Pp]sychologie|[Pp]sychologic|" +
                "[Ss]ociální|[Ss]ociolog|[Ss]tátní\\sspráv|[Hh]istorie|[Hh]istoric|[Žž]urnalist|\\sFF|FMV|ÚSP)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VSFields[8];
        }

        regex = "(([Tt]eologi(e|cká))|\\sTS|\\sTF|KTF|ETF|HTF|CMTF|)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VSFields[9];
        }


        regex = "([Uu]měleck|[Uu]mění|[Hh]udební|[Kk]onzerva|[Tt]aneční|[Ff]ilmová|[Gg]rafi|[Ff]otog" +
                "|FAMU|DAMU|HAMU|JAMU|\\sFH|\\sFU|DIFA|F[Aa]VU|FUD|FMK|FDULS|UUD|FUD)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VSFields[10];
        }

        regex = "([Bb]ezpečnost|[Vv]ojensk[ýéá]|[Oo]chran|[Pp]olicejní|FVL|FVT|FBP|FBI|FBM)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VSFields[11];
        }

        regex = "([Dd]opravní|[Dd]oprava|DFJP|\\sFD\\s|)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VSFields[12];
        }

        regex = "([Pp]řírodověd|[Pp]řírodní(ch)?\\svěd|[Ee]kologi|[Cc]hemick|[Cc]hemie|[Ff]yzik|[Bb]iolog|[Jj]adern" +
                "|[Mm]atemati|[Ss]tatisti|P[Řř]F|FJFI|FCHI|\\sFCH||FZP|FPF|FCHT|HGF|FAV|\\sFP|MFF)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VSFields[13];
        }

        regex = "([Ll]ingvisti|[Jj]azyková)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VSFields[14];
        }

        return null;
    }


    public String findFieldForVOSSSMatLevel(String extractedText, int sectionStart) {
        String[] VOSSSMatFields = {
                "Informatika_a_management",
                "Elektrotechnika_a_technika",
                "Obchod_a_ekonomie",
                "Zdravotnictvi_a_medicina",
                "Pravo",
                "Gymnazium",
                "Stavebnictvi",
                "Zemedelstvi_a_lesnictvi",
                "Pedagogika,ucitelstvi_a_telovychova",
                "Filosofie,politologie,historie,zurnalistika,psychologie,sociologie,verejna_sprava",
                "Hotelnictvi,cestovni_ruch,gastronomie,restauratorstvi",
                "Teologie",
                "Umeni",
                "Obrana_a_ochrana",
                "Doprava",
                "Prirodni_vedy,chemie_fyzika_matematika",
                "Lingvistika"
        };

        String regex = "([Ii]nformatik[ay]|[Ii]nformační|[Mm]anagement|[Pp]očítač|\\sIT\\s|ICT" +
                "|[Kk]omunikač|[Kk]ybernet|[Vv]ýpočetní)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VOSSSMatFields[0];
        }


        regex = "([Ee]lektrotechnik[ay]|[Ee]lektron|[Tt]elekomunika|[Aa]utomatizac|[Mm]echani|[Ss](ilno|labo)proud" +
                "|[Ee]lektrik|[Ss]troj|[Tt]echni|[Rr]obot|tronik|[Aa]uto|SPŠ)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VOSSSMatFields[1];
        }


        regex = "([Oo]bchod|[Ff]inan|[Ee]konomi|[Úú]četnic|[Pp]odnikatel)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VOSSSMatFields[2];
        }


        regex = "([Zz]dravotn|[Ff]armac|[Ss]estra|[Zz]ubař|[Vv]eterin|SZS|[Zz]dravotnický [Aa]sistent|[Zz]áchraná)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VOSSSMatFields[3];
        }


        regex = "([Pp]rávní)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VOSSSMatFields[4];
        }


        regex = "([Gg]ymn[aá]zium)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VOSSSMatFields[5];
        }


        regex = "([Ss]tavebn[ií]|[Aa]rchitekt|[Gg]eod)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VOSSSMatFields[6];
        }


        regex = "([Zz]eměděls|[Aa]gro|[Ll]esnic|[Rr]ybářství|[Zz]ahradn)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VOSSSMatFields[7];
        }


        regex = "([Pp]edagogi|[Uu]čitels|[Tt]ělových|[Tt]ělesné|[Ss]port|SPgŠ)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VOSSSMatFields[8];
        }


        regex = "([Pp]sychologie|[Pp]sychologic|[Ss]ociáln[ěí]|[Ss]ociolog|[Ss]tátní\\sspráv" +
                "|[Hh]istorie|[Hh]istoric|[Dd]ějin|[Žž]urnalist)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VOSSSMatFields[9];
        }


        regex = "([Hh]otel[no]|[Rr]estaurat|[Gg]astro|[Cc]estovní|[Ss]lužeb)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VOSSSMatFields[10];
        }


        regex = "(([Tt]eologi(e|cká)))";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VOSSSMatFields[11];
        }


        regex = "([Uu]měleck|[Uu]mění|[Hh]udební|[Kk]onzerva|[Tt]aneční|[Ff]ilmová|[Gg]rafi" +
                "|[Ff]otograf|[Mm]ediální|[Pp]olygraf)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VOSSSMatFields[12];
        }


        regex = "([Bb]ezpečnost|[Vv]ojensk[ýéá]|[Oo]chran|[Pp]olicejní|[Hh]asič)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VOSSSMatFields[13];
        }


        regex = "([Dd]opravní|[Dd]oprava|[Ii]nfrastruktur)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VOSSSMatFields[14];
        }


        regex = "([Pp]řírodověd|[Pp]řírodní(ch)?\\svěd|[Ee]kologi|[Cc]hemick|[Cc]hemie|[Ff]yzik|" +
                "[Bb]iolog|[Jj]adern|[Mm]atemati|[Ss]tatisti)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VOSSSMatFields[15];
        }


        regex = "([Ll]ingvisti|[Jj]azyková)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return VOSSSMatFields[16];
        }

        return null;
    }

    public String findFieldForSSLevel(String extractedText, int sectionStart) {
        String[] SSFields = {
                "Elektrotechnika_technika_mechanika_prumysl",
                "Zdravotnictvi_a_medicina",
                "Stavebnictvi",
                "Zemedelstvi_lesnictvi_dřevozpracovani",
                "Sluzby_hotelnictvi_cestovni_ruch,gastronomie,restauratorstvi",
                "Umeni",
                "Obrana_a_ochrana",
                "Remeslna_vyroba"
        };

        String regex = "([Ee]lektrotech|[Ee]lektronik|[Mm]echani[ck]|[Aa]utomatiz|[Rr]oboti|[Ss](ilno|labo)proud" +
                "|[Ee]lektrik|[Tt]echni|[Rr]obot|tronik|[Aa]uto|[Oo]pravář|[Mm]ontér|SPŠ)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return SSFields[0];
        }


        regex = "([Zz]dravot|[Oo]šetřov|[Pp]ečovat|[Ss]estra)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return SSFields[1];
        }


        regex = "([Zz]edn[ií]|[Kk]onstru|[Ss]tavebn|[Pp]okrývač|[Gg]eod|[Zz]eměměřič)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return SSFields[2];
        }


        regex = "([Zz]eměděl|[Dd]řevo|[Zz]pracovatel|[Vv]odař|[Ll]esn[ií])";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return SSFields[3];
        }


        regex = "([Ss]luž[be]|[Hh]otel|[Cc]estovní(ho)?\\n[Rr]uch|[Gg]astronomi|[Rr]estraura|[Aa]ranžér" +
                "|[Ll]akýrník|[Cc]ukrář|[Čč]aloun[ií]|[Ii]nstalatér|[Kk]adeřn[ií]|[Kk]rejč[oí]" +
                "|[Kk]uchař|[Čč]íšn[ií]|[Mm]alíř|[Mm]anipulant|[Oo]perátor|[Pp]ekař|[Pp]otravin" +
                "|[Pp]rodavač|[Pp]rovozní|[Mm]asér|[Řř]ezník|[Uu]zenář|[Šš]i(tí|čka)|[Tt]extil" +
                "|[Mm]askér|[Zz]ahradn)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return SSFields[4];
        }

        regex = "([Uu]měleck[áý]|[Uu]mění|[Gg]rafi[ck]|[Bb]ižuter)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return SSFields[5];
        }

        regex = "([Oo]bran[ay]|[Oo]chran[ay]|[Hh]lídač|[Ss]trážný)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return SSFields[6];
        }



        regex = "([Hh]utn[ií][ck]|[Kk]ameník|[Kk]amnař|[Kk]erami|[Kk]lempíř|[Kk]nihař|[Kk]omin[ií]|[Kk]ožeděl" +
                "|[Ll]odník|[Mm]odelář|[Oo]bráběč|[Ss]troj|[Kk]arosář|[Nn]ástrojař|[Pp]odkovář|[Kk]ovář|[Dd]laždič" +
                "|[Pp]odlahář|[Pp]uškař|[Ss]klář|[Ss]klenář|[Ss]lévač|[Tt]esař|[Tt]iskař|[Tt]ruhlář|[Vv]čelař" +
                "|[Žž]elezničář)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            return SSFields[7];
        }

        return null;
    }*/

   public void getPredictions() throws IOException {
       // Make a URL to the web page
       URL url = new URL("https://fieldpredictor.herokuapp.com/prediction");

       // Get the input stream through URL Connection
       URLConnection con = url.openConnection();
       InputStream is =con.getInputStream();

       // Once you have the Input Stream, it's just plain old Java IO stuff.

       // For this case, since you are interested in getting plain-text web page
       // I'll use a reader and output the text content to System.out.

       // For binary content, it's better to directly read the bytes from stream and write
       // to the target file.


       BufferedReader br = new BufferedReader(new InputStreamReader(is));

       String line = null;

       // read each line and write to System.out
       while ((line = br.readLine()) != null) {
           System.out.println(line);
       }
   }
}