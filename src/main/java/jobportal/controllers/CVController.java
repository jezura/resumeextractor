package jobportal.controllers;
import jobportal.utils.PdfExtractor;
import jobportal.utils.WordExtractor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class CVController {

    @RequestMapping(value = "/loadCvFile")
    public String showLoadCvFileForm(Model model) {
       // CVFile cvFile = new CVFile();
        //model.addAttribute("cvFile", cvFile);
        return "loadCvFile";
    }

    @RequestMapping(value = "/processCv", method = RequestMethod.POST)
    public String processCV(Model model, @RequestParam("file") MultipartFile[] files) throws IOException {
        String fileName = files[0].getOriginalFilename();
        Path fileNameAndPath = Paths.get("D:\\",fileName);
        try {
            Files.write(fileNameAndPath,files[0].getBytes());
        } catch (IOException e) {
                e.printStackTrace();
        }

        File savedFile = new File("D:\\" + files[0].getOriginalFilename());
        if (fileName.endsWith(".pdf"))
        {
            PdfExtractor pdfExtractor = new PdfExtractor();
            System.out.print(pdfExtractor.getPdfTextData(savedFile));
            System.out.println("Vypisuji PDF cv");

            String regexEmail = "(\\s?[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-z]+\\s?)";
            Pattern pattern = Pattern.compile(regexEmail);
            Matcher matcher = pattern.matcher(pdfExtractor.getPdfTextData(savedFile));

            while (matcher.find())
            {
                System.out.print("Found email: " + matcher.group());
            }

        }else
            if ((fileName.endsWith(".docx")) || (fileName.endsWith(".doc"))) {
                WordExtractor wordExtractor = new WordExtractor();
                System.out.println("Vypisuji word cv");
                System.out.print(wordExtractor.getWordTextData(savedFile));
            }


        return "redirect:/";
    }
}