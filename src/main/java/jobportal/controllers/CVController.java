package jobportal.controllers;
import jobportal.models.CVFile;
import jobportal.utils.PdfExtractor;
import jobportal.utils.WordExtractor;
import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.core.io.ClassPathResource;
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

    @RequestMapping(value = "/loadCvFile")
    public String showLoadCvFileForm(Model model) {
       // CVFile cvFile = new CVFile();
        //model.addAttribute("cvFile", cvFile);
        return "loadCvFile";
    }

    @RequestMapping(value = "/processCv", method = RequestMethod.POST)
    public String processCV(Model model, @RequestParam("file") MultipartFile[] files) throws IOException {

        StringBuilder fileNames = new StringBuilder();
        for (MultipartFile file : files) {
            Path fileNameAndPath = Paths.get("D:\\",file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            try {
                Files.write(fileNameAndPath,file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PdfExtractor pdfExtractor = new PdfExtractor();
        //File file = new ClassPathResource("D:\\" + files[0].getOriginalFilename()).getFile();
        File file = new File("D:\\" + files[0].getOriginalFilename());
        System.out.print(pdfExtractor.getPdfTextData(file));
        //Path fileNameAndPath = Paths.get("D:\\",file.getName());
        //Files.write(fileNameAndPath,file.)

        return "redirect:/";
    }

    @GetMapping(value = "/admin/extract-cv")
    public void extractCV() throws IOException {
        PdfExtractor pdfExtractor = new PdfExtractor();
        //System.out.print(pdfExtractor.getPdfTextData());
    }

    @GetMapping(value = "/admin/extract-word-cv")
    public void extractWordCV() throws IOException {
        WordExtractor wordExtractor = new WordExtractor();
        System.out.print(wordExtractor.getWordTextData());
    }
}