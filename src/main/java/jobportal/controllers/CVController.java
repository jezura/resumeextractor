package jobportal.controllers;
import jobportal.models.cv_support.CzechName;
import jobportal.models.cv_support.MaxEducation;
import jobportal.models.cv_support.Title;
import jobportal.services.CzechNameService;
import jobportal.services.TitleService;
import jobportal.utils.CVExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class CVController {
    @Autowired
    private CzechNameService czechNameService;
    @Autowired
    private TitleService titleService;

    @RequestMapping(value = "/loadCvFile")
    public String showLoadCvFileForm() {
        return "loadCvFile";
    }

    @RequestMapping(value = "/processCv", method = RequestMethod.POST)
    public String processCV(Model model, @RequestParam("file") MultipartFile[] files) throws IOException {
        CVExtractor cvExtractor = new CVExtractor();

        String fileName = files[0].getOriginalFilename();
        Path fileNameAndPath = Paths.get("D:\\",fileName);
        try {
            Files.write(fileNameAndPath,files[0].getBytes());
        } catch (IOException e) {
                e.printStackTrace();
        }

        File savedFile = new File("D:\\" + files[0].getOriginalFilename());

        String extractedText = cvExtractor.getCvTextData(savedFile, fileName);
        System.out.print(extractedText);

        String extractedEmail = cvExtractor.extractEmail(extractedText);

        String extractedMobile = cvExtractor.extractMobile(extractedText);

        LocalDate extractedBirthDate = cvExtractor.extractBirthDate(extractedText);

        CzechName extractedFirstName = cvExtractor.extractFirstName(extractedText, czechNameService.findAllCzechNames(), 40);

        String extractedLastName = cvExtractor.extractLastName(extractedText, extractedFirstName.getName());

        List<Title> extractedTitleList = cvExtractor.extractTitle(extractedText, titleService.findAllTitles());

        MaxEducation maxEdu = cvExtractor.extractMaxEducationAndGeneralEduField(extractedText, extractedTitleList);


        System.out.println("Email: " + extractedEmail);
        System.out.println("Mobile: " + extractedMobile);
        if (extractedBirthDate != null) {
            long years;
            if(extractedBirthDate.getYear() < 1950)
            {
                extractedBirthDate = extractedBirthDate.plusYears(100);
                System.out.println("Birth year: " + extractedBirthDate.getYear());
                years = ChronoUnit.YEARS.between(extractedBirthDate, LocalDate.now());
                System.out.println("Age: " + years);
            } else {
                System.out.println("Birth date: " + extractedBirthDate.toString());
                years = ChronoUnit.YEARS.between(extractedBirthDate, LocalDate.now());
                System.out.println("Age: " + years);
            }
        } else {
            System.out.println("Birth date and age not found");
        }

        System.out.println("First name: " + extractedFirstName.getName());
        System.out.println("Last name: " + extractedLastName);
        System.out.println("Gender: " + extractedFirstName.getGender());
        for (Title title:extractedTitleList) {
            System.out.println("Titul: " + title.getOfficialVersion());
        }
        System.out.println("Nejvyšší dosažený stupeň vzdělání: " + maxEdu.getMaxEduLvl().getMaxEduLvlName());
        System.out.println("Obecný obor studia nejvyššího dosaženého vzdělání: " + maxEdu.getGeneralEduField());

        cvExtractor.getPredictions();

        return "redirect:/";
    }
}