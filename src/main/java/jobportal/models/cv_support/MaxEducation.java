package jobportal.models.cv_support;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaxEducation {

    private MaxEduLvl maxEduLvl;
    private String generalEduField;

    public MaxEducation() {
    }

    public MaxEducation(MaxEduLvl maxEduLvl, String generalEduField) {
        this.maxEduLvl = maxEduLvl;
        this.generalEduField = generalEduField;
    }

    public MaxEduLvl getMaxEduLvl() {
        return maxEduLvl;
    }

    public void setMaxEduLvl(MaxEduLvl maxEduLvl) {
        this.maxEduLvl = maxEduLvl;
    }

    public String getGeneralEduField() {
        return generalEduField;
    }

    public void setGeneralEduField(String generalEduField) {
        this.generalEduField = generalEduField;
    }

    public boolean findFieldForVSLevel(String extractedText, Boolean useAreaIndexes) {
        String textAreaSubString;

        String regex;
        Pattern pattern;
        Matcher matcher;

        if((maxEduLvl.getEndPosIndex() == 0)||(useAreaIndexes == false)) {
            textAreaSubString = extractedText;
            System.out.println("MaxEducation-findFieldforVSLevel: Hledam pomoci original extractedTextu");
        } else {
            textAreaSubString = extractedText.substring(maxEduLvl.getStartPosIndex(), maxEduLvl.getEndPosIndex());
            System.out.println("MaxEducation-findFieldforVSLevel: Hledam pomoci substringu jen v okolni oblasti");
        }

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

        regex = "([Ii]nformatik[ay]|[Ii]nformační|[Mm]anagement|[Dd]at[ao]|FIM|FIT|\\sFI|FIS|FAI)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VSFields[0]);
            return true;
        }


        regex = "([Ee]lektrotechnik[ay]|[Ee]lektron|[Tt]elekomunika|[Aa]utomatizac|FEL|FEKT)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VSFields[1]);
            return true;
        }


        regex = "([Oo]bchod|[Ff]inan|[Ee]konomi|\\sNF|FPH|ECON|FEK|FSE|FES|EKF|OPF|F[Aa]ME|PEF)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VSFields[2]);
            return true;
        }


        regex = "([Zz]dravotn|[Ff]armac|[Ss]estra|[Zz]ubař|[Ll]ékař|[Mm]edicín|[Vv]eterin|[Ff]yzioterapie|FVZ|\\sLF|FVL|VFU|FZS)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VSFields[3]);
            return true;
        }


        regex = "([Pp]ráv[ao]|[Pp]rávní|[Pp]rávnic|P[Rr]F|FPR|\\sFP|\\sPF)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VSFields[4]);
            return true;
        }


        regex = "([Ss]tavebn[ií]|[Aa]rchitekt|FAST|FSv)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VSFields[5]);
            return true;
        }


        regex = "([Zz]eměděls|[Aa]gro|[Ll]esnic|[Rr]ybářství|[Zz]ahradnic|FLD|LDF|FROV|FAPPZ|\\sZF|\\sAF)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VSFields[6]);
            return true;
        }


        regex = "([Pp]edagogi|[Uu]čitels|[Tt]ělových|[Tt]ělesné|[Ss]port|FTVS|FTK|FPE|P[Dd]F)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VSFields[7]);
            return true;
        }


        regex = "([Ff]ilosofie|[Ff]ilosofick|[Pp]olitologie|[Pp]olitologick|[Pp]sychologie|[Pp]sychologic|" +
                "[Ss]ociální|[Ss]ociolog|[Ss]tátní\\sspráv|[Hh]istorie|[Hh]istoric|[Žž]urnalist|\\sFF|FMV|ÚSP)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VSFields[8]);
            return true;
        }


        regex = "(([Tt]eologi(e|cká))|\\sTS|\\sTF|KTF|ETF|HTF|CMTF|)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VSFields[9]);
            return true;
        }


        regex = "([Uu]měleck|[Uu]mění|[Hh]udební|[Kk]onzerva|[Tt]aneční|[Ff]ilmová|[Gg]rafi|[Ff]otog" +
                "|FAMU|DAMU|HAMU|JAMU|\\sFH|\\sFU|DIFA|F[Aa]VU|FUD|FMK|FDULS|UUD|FUD)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VSFields[10]);
            return true;
        }


        regex = "([Bb]ezpečnost|[Vv]ojensk[ýéá]|[Oo]chran|[Pp]olicejní|FVL|FVT|FBP|FBI|FBM)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VSFields[11]);
            return true;
        }


        regex = "([Dd]opravní|[Dd]oprava|DFJP|\\sFD\\s|)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VSFields[12]);
            return true;
        }


        regex = "([Pp]řírodověd|[Pp]řírodní(ch)?\\svěd|[Ee]kologi|[Cc]hemick|[Cc]hemie|[Ff]yzik|[Bb]iolog|[Jj]adern" +
                "|[Mm]atemati|[Ss]tatisti|P[Řř]F|FJFI|FCHI|\\sFCH||FZP|FPF|FCHT|HGF|FAV|\\sFP|MFF)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VSFields[13]);
            return true;
        }


        regex = "([Ll]ingvisti|[Jj]azyková)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VSFields[14]);
            return true;
        }

        //nepodarilo se najit zadnou shodu => zadny obecny obor nebyl identifikovan
        System.out.println("Nenalezl jsem obor");
        return false;
    }

    public boolean findFieldForVOSSSMatLevel(String extractedText, Boolean useAreaIndexes) {
        String textAreaSubString;

        String regex;
        Pattern pattern;
        Matcher matcher;

        if((maxEduLvl.getEndPosIndex() == 0)||(useAreaIndexes == false)) {
            textAreaSubString = extractedText;
            System.out.println("MaxEducation-findFieldforVOSSSMatLevel: Hledam pomoci original extractedTextu");
        } else {
            textAreaSubString = extractedText.substring(maxEduLvl.getStartPosIndex(), maxEduLvl.getEndPosIndex());
            System.out.println("MaxEducation-findFieldforVOSSSMatLevel: Hledam pomoci substringu jen v okolni oblasti");
        }

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

        regex = "([Ii]nformatik[ay]|[Ii]nformační|[Mm]anagement|[Pp]očítač|\\sIT\\s|ICT" +
                "|[Kk]omunikač|[Kk]ybernet|[Vv]ýpočetní)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VOSSSMatFields[0]);
            return true;
        }


        regex = "([Ee]lektrotechnik[ay]|[Ee]lektron|[Tt]elekomunika|[Aa]utomatizac|[Mm]echani|[Ss](ilno|labo)proud" +
                "|[Ee]lektrik|[Ss]troj|[Tt]echni|[Rr]obot|tronik|[Aa]uto|SPŠ)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VOSSSMatFields[1]);
            return true;
        }


        regex = "([Oo]bchod|[Ff]inan|[Ee]konomi|[Úú]četnic|[Pp]odnikatel)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VOSSSMatFields[2]);
            return true;
        }


        regex = "([Zz]dravotn|[Ff]armac|[Ss]estra|[Zz]ubař|[Vv]eterin|SZS|[Zz]dravotnický [Aa]sistent|[Zz]áchraná)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VOSSSMatFields[3]);
            return true;
        }


        regex = "([Pp]rávní)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VOSSSMatFields[4]);
            return true;
        }


        regex = "([Gg]ymn[aá]zium)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VOSSSMatFields[5]);
            return true;
        }


        regex = "([Ss]tavebn[ií]|[Aa]rchitekt|[Gg]eod)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VOSSSMatFields[6]);
            return true;
        }


        regex = "([Zz]eměděls|[Aa]gro|[Ll]esnic|[Rr]ybářství|[Zz]ahradn)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VOSSSMatFields[7]);
            return true;
        }


        regex = "([Pp]edagogi|[Uu]čitels|[Tt]ělových|[Tt]ělesné|[Ss]port|SPgŠ)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VOSSSMatFields[8]);
            return true;
        }


        regex = "([Pp]sychologie|[Pp]sychologic|[Ss]ociáln[ěí]|[Ss]ociolog|[Ss]tátní\\sspráv" +
                "|[Hh]istorie|[Hh]istoric|[Dd]ějin|[Žž]urnalist)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VOSSSMatFields[9]);
            return true;
        }


        regex = "([Hh]otel[no]|[Rr]estaurat|[Gg]astro|[Cc]estovní|[Ss]lužeb)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VOSSSMatFields[10]);
            return true;
        }


        regex = "(([Tt]eologi(e|cká)))";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VOSSSMatFields[11]);
            return true;
        }


        regex = "([Uu]měleck|[Uu]mění|[Hh]udební|[Kk]onzerva|[Tt]aneční|[Ff]ilmová|[Gg]rafi" +
                "|[Ff]otograf|[Mm]ediální|[Pp]olygraf)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VOSSSMatFields[12]);
            return true;
        }


        regex = "([Bb]ezpečnost|[Vv]ojensk[ýéá]|[Oo]chran|[Pp]olicejní|[Hh]asič)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VOSSSMatFields[13]);
            return true;
        }


        regex = "([Dd]opravní|[Dd]oprava|[Ii]nfrastruktur)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VOSSSMatFields[14]);
            return true;
        }


        regex = "([Pp]řírodověd|[Pp]řírodní(ch)?\\svěd|[Ee]kologi|[Cc]hemick|[Cc]hemie|[Ff]yzik|" +
                "[Bb]iolog|[Jj]adern|[Mm]atemati|[Ss]tatisti)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VOSSSMatFields[15]);
            return true;
        }


        regex = "([Ll]ingvisti|[Jj]azyková)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(VOSSSMatFields[16]);
            return true;
        }

        //nepodarilo se najit zadnou shodu => zadny obecny obor nebyl identifikovan
        System.out.println("Nenalezl jsem obor");
        return false;
    }

    public boolean findFieldForSSLevel(String extractedText, Boolean useAreaIndexes) {
        String textAreaSubString;

        String regex;
        Pattern pattern;
        Matcher matcher;

        if((maxEduLvl.getEndPosIndex() == 0)||(useAreaIndexes == false)) {
            textAreaSubString = extractedText;
            System.out.println("MaxEducation-findFieldforSSLevel: Hledam pomoci original extractedTextu");
        } else {
            textAreaSubString = extractedText.substring(maxEduLvl.getStartPosIndex(), maxEduLvl.getEndPosIndex());
            System.out.println("MaxEducation-findFieldforSSLevel: Hledam pomoci substringu jen v okolni oblasti");
        }

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

        regex = "([Ee]lektrotech|[Ee]lektronik|[Mm]echani[ck]|[Aa]utomatiz|[Rr]oboti|[Ss](ilno|labo)proud" +
                "|[Ee]lektrik|[Tt]echni|[Rr]obot|tronik|[Aa]uto|[Oo]pravář|[Mm]ontér|SPŠ)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(SSFields[0]);
            return true;
        }


        regex = "([Zz]dravot|[Oo]šetřov|[Pp]ečovat|[Ss]estra)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(SSFields[1]);
            return true;
        }


        regex = "([Zz]edn[ií]|[Kk]onstru|[Ss]tavebn|[Pp]okrývač|[Gg]eod|[Zz]eměměřič)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(SSFields[2]);
            return true;
        }


        regex = "([Zz]eměděl|[Dd]řevo|[Zz]pracovatel|[Vv]odař|[Ll]esn[ií])";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(SSFields[3]);
            return true;
        }


        regex = "([Ss]luž[be]|[Hh]otel|[Cc]estovní(ho)?\\s[Rr]uch|[Gg]astronomi|[Rr]estraura|[Aa]ranžér" +
                "|[Ll]akýrník|[Cc]ukrář|[Čč]aloun[ií]|[Ii]nstalatér|[Kk]adeřn[ií]|[Kk]rejč[oí]" +
                "|[Kk]uchař|[Čč]íšn[ií]|[Mm]alíř|[Mm]anipulant|[Oo]perátor|[Pp]ekař|[Pp]otravin" +
                "|[Pp]rodavač|[Pp]rovozní|[Mm]asér|[Řř]ezník|[Uu]zenář|[Šš]i(tí|čka)|[Tt]extil" +
                "|[Mm]askér|[Zz]ahradn)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(SSFields[4]);
        }

        regex = "([Uu]měleck[áý]|[Uu]mění|[Gg]rafi[ck]|[Bb]ižuter)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(SSFields[5]);
        }

        regex = "([Oo]bran[ay]|[Oo]chran[ay]|[Hh]lídač|[Ss]trážný)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(SSFields[6]);
            return true;
        }



        regex = "([Hh]utn[ií][ck]|[Kk]ameník|[Kk]amnař|[Kk]erami|[Kk]lempíř|[Kk]nihař|[Kk]omin[ií]|[Kk]ožeděl" +
                "|[Ll]odník|[Mm]odelář|[Oo]bráběč|[Ss]troj|[Kk]arosář|[Nn]ástrojař|[Pp]odkovář|[Kk]ovář|[Dd]laždič" +
                "|[Pp]odlahář|[Pp]uškař|[Ss]klář|[Ss]klenář|[Ss]lévač|[Tt]esař|[Tt]iskař|[Tt]ruhlář|[Vv]čelař" +
                "|[Žž]elezničář)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(extractedText);

        if (matcher.find()) {
            setGeneralEduField(SSFields[7]);
            return true;
        }

        if(useAreaIndexes) {
            //nepodarilo se najit zadnou shodu => zadny obecny obor nebyl identifikovan
            System.out.println("Nenalezl jsem obor");
            return false;
        }else{ //pokud se jedna uz o druhy pokus s plosnym hledanim v celem textu
            //natvrdo nastavit "Remeslna_vyroba"
            setGeneralEduField(SSFields[7]);
            return true;
        }
    }
}