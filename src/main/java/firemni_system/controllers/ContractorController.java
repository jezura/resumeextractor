package firemni_system.controllers;

import firemni_system.models.*;
import firemni_system.security.MyUser;
import firemni_system.services.CiselnikyService;
import firemni_system.services.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;

@Controller
public class ContractorController {
    @Autowired
    private PersonService personService;
    @Autowired
    private CiselnikyService ciselnikyService;


    @GetMapping(value = "/")
    public String init(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser userDetails = MyUser.class.cast(principal);
        String name = userDetails.getUsername();
        model.addAttribute("name", name);
        return "index";
    }

    @GetMapping(value = "/validator/allContractors")
    public String showAllContractors(Model model){
        Collection<Contractor> contractors = personService.findAllContractors();
        model.addAttribute("contractorsList", contractors);
        return "validator/allContractors";
    }

    @GetMapping(value = "/manager/allContractors")
    public String showAllContractorsForManager(Model model){
        Collection<Contractor> contractors = personService.findAllContractors();
        model.addAttribute("contractorsList", contractors);
        return "manager/allContractors";
    }

    @GetMapping(value = "/manager/newContractor")
    public String showAddContractorForm(Model model){

        populateWithData(model);
        Contractor contractor = new Contractor();
        model.addAttribute("contractor", contractor);
        return "manager/addContractor";
    }

    @RequestMapping(value = "/manager/saveContractor", method = RequestMethod.POST)
    public String saveContractor(@Valid @ModelAttribute("contractor") Contractor contractor, BindingResult bindingResult, Model model) {
        bindingResult.getErrorCount();
        if (bindingResult.hasErrors()) {
            populateWithData(model);
            return "manager/addContractor";
        }
        contractor.setRole("CONTRACTOR");
        personService.saveContractor(contractor);
        return "redirect:/manager/allContractorsForManager";
    }

    @RequestMapping(value = "/manager/updateContractor", method = RequestMethod.POST)
    public String updateContractor(@Valid @ModelAttribute("contractor") Contractor contractor, BindingResult bindingResult, Model model) {

        bindingResult.getErrorCount();
        if (bindingResult.hasErrors()) {
            populateWithData(model);
            return "manager/editContractor";
        }
        contractor.setRole("CONTRACTOR");
        personService.saveContractor(contractor);
        return "redirect:/manager/allContractors";
    }

    @RequestMapping(value = "/manager/deleteContractor/{id}")
    public String deleteContractor(@PathVariable(name = "id") int id) {
        personService.deleteContractor(id);
        return "redirect:/manager/allContractors";
    }

    @RequestMapping(value = "/manager/editContractor/{id}")
    public ModelAndView showEditContractorForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("manager/editContractor");
        populateWithDataEdit(mav);
        Contractor contractor = personService.getContractor(id);
        mav.addObject("contractor", contractor);
        return mav;
    }

    private void populateWithData(Model model){
        Collection<Validator> validators = personService.findAllValidators();
        Collection<Team> teams = ciselnikyService.findAllTeams();
        Collection<SwimlaneType> swimlanes = ciselnikyService.findAllSwimlanes();
        model.addAttribute("teams",teams);
        model.addAttribute("swimlanes", swimlanes);
        model.addAttribute("validators", validators);
    }

    private void populateWithDataEdit(ModelAndView mav){
        Collection<Validator> validators = personService.findAllValidators();
        Collection<Team> teams = ciselnikyService.findAllTeams();
        Collection<SwimlaneType> swimlanes = ciselnikyService.findAllSwimlanes();
        mav.addObject("teams",teams);
        mav.addObject("swimlanes", swimlanes);
        mav.addObject("validators", validators);
    }
}