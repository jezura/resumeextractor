package jobportal.controllers;
import jobportal.services.CzechNameService;
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

@Controller
public class CVController {
    @Autowired
    private CzechNameService czechNameService;

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
        System.out.println(extractedEmail);
        String extractedFirstName = cvExtractor.extractFirstName(extractedText, czechNameService.findAllCzechNames());
        System.out.println(extractedFirstName);
        String extractedLastName = cvExtractor.extractLastName(extractedText, extractedFirstName);
        System.out.println(extractedLastName);

        /*if (fileName.endsWith(".pdf"))
        {

            System.out.print(cvExtractor.getPdfTextData(savedFile));
            System.out.println("Vypisuji PDF cv");




            for (CzechName name : czechNameService.findAllCzechNames()) {
                System.out.println(name.getName() + "  gender: " + name.getGender());
            }

        } else
            if ((fileName.endsWith(".docx")) || (fileName.endsWith(".doc"))) {
                System.out.println("Vypisuji word cv");
                System.out.print(cvExtractor.getWordTextData(savedFile));
            }*/


        return "redirect:/";
    }
}