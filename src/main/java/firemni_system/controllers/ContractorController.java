package firemni_system.controllers;

import firemni_system.services.PersonService;
import firemni_system.models.Contractor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class ContractorController {

    @Autowired
    private PersonService personService;


    @GetMapping(value = "/")
    public String init(){
        return "index";
    }

    @GetMapping(value = "/validator/allContractors")
    public String showAllContractors(Model model){
        Collection<Contractor> contractors = personService.findAllContractors();
        model.addAttribute("contractorsList", contractors);
        return "validator/allContractors";
    }

    @GetMapping(value = "/manager/newContractor")
    public String showAddContractorForm(Model model){
        Contractor contractor = new Contractor();
        model.addAttribute("contractor", contractor);
        return "manager/addContractor";
    }

    @RequestMapping(value = "/manager/saveContractor", method = RequestMethod.POST)
    public String saveContractor(@ModelAttribute("contractor") Contractor contractor) {
        personService.saveContractor(contractor);
        return "redirect:/validator/allContractors";
    }

    @RequestMapping(value = "/manager/deleteContractor/{id}")
    public String deleteContractor(@PathVariable(name = "id") int id) {
        personService.deleteContractor(id);
        return "redirect:/validator/allContractors";
    }

    @RequestMapping(value = "/manager/editContractor/{id}")
    public ModelAndView showEditContractorForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("manager/editContractor");
        Contractor contractor = personService.getContractor(id);
        mav.addObject("contractor", contractor);
        return mav;
    }
}