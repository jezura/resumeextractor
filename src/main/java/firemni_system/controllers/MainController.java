package firemni_system.controllers;

import firemni_system.services.PersonService;
import firemni_system.workers.Contractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
public class MainController {
    @Autowired
    private PersonService personService;

    @GetMapping(value = "/")
    public String init(){
        return "index.html";
    }
    @GetMapping(value = "/delete")
  public void deleteContractor(@RequestParam int id){
        personService.delete(id);

    };


    @GetMapping(value = "/allContractors")
    @ResponseBody
    public Collection<Contractor> getAllContractors(){

return personService.findAllContractors();
    }
}
