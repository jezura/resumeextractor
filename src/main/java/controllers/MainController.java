package controllers;

import com.uhk.ppro.firemni_system.entity.workers.Contractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.PersonService;

import java.util.Collection;

@Controller
public class MainController {
    @Autowired
    private PersonService personService;

    @GetMapping(value = "/")
    public String init(){
        return "index";
    }
    @GetMapping(value = "/delete")
  public void deleteContractor(@RequestParam int id){
        personService.delete(id);

    };

    @GetMapping(value = "/allContractors")
    public Collection<Contractor> getAllContractors(){
return personService.findAllContractors();
    }
}
