package jobportal.controllers;

import jobportal.models.*;
import jobportal.services.*;
import jobportal.utils.PdfExtractor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@RestController
public class CVController {

    @GetMapping(value = "/admin/extract-cv")
    public void extractCV() throws IOException {
        PdfExtractor pdfExtractor = new PdfExtractor();
        System.out.print(pdfExtractor.getPdfTextData());
    }
}