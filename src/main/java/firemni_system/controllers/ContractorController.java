package firemni_system.controllers;

import firemni_system.models.*;
import firemni_system.security.MyUser;
import firemni_system.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.Collection;

@Controller
public class ContractorController {
    private String message_notification = "";
    @Autowired
    private PersonService personService;
    @Autowired
    private CiselnikyService ciselnikyService;
    @Autowired
    private WorksService worksService;
    @Autowired
    private PlanService planService;
    @Autowired
    private DomainService domainService;

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
        model.addAttribute("contractors", contractors);
        model.addAttribute("message_notification", message_notification);
        message_notification = "";
        return "validator/allContractors";
    }

    @RequestMapping(value = "/SearchContractors")
    public String showFilteredContractors(Model model, @RequestParam(name = "name", required = false)  String name){
        Collection<Contractor> contractors = personService.findContractorsByFirstNameLastName(name);
        model.addAttribute("contractors", contractors);
        return "validator/allContractors";
    }

    @GetMapping(value = "/manager/newContractor")
    public String showAddContractorForm(Model model){
        populateWithData(model);
        Contractor contractor = new Contractor();
        model.addAttribute("contractor", contractor);
        model.addAttribute("message_notification", "");
        return "manager/addContractor";
    }

    @RequestMapping(value = "/manager/saveContractor", method = RequestMethod.POST)
    public String saveContractor(@Valid @ModelAttribute("contractor") Contractor contractor, BindingResult bindingResult, Model model) {

        if(!personService.isUnique(contractor.getLogin())){
            FieldError error = new FieldError("contractor", "login",
                    "User with this login already exists");
            bindingResult.addError(error);
        }
        bindingResult.getErrorCount();
        if (bindingResult.hasErrors()) {
            populateWithData(model);
            return "manager/addContractor";
        }
        contractor.setRole("CONTRACTOR");
        String encodedPassword = new BCryptPasswordEncoder().encode(contractor.getPassword());
        contractor.setPassword(encodedPassword);
        personService.saveContractor(contractor);
        message_notification = "Nový kontraktor byl úspěšně přidán";
        return "redirect:/validator/allContractors";
    }

    @RequestMapping(value = "/manager/updateContractor", method = RequestMethod.POST)
    public String updateContractor(@Valid @ModelAttribute("contractor") Contractor contractor, BindingResult bindingResult, Model model) {

        bindingResult.getErrorCount();
        if (bindingResult.hasErrors()) {
            populateWithData(model);
            return "manager/editContractor";
        }
        contractor.setRole("CONTRACTOR");
        String encodedPassword = new BCryptPasswordEncoder().encode(contractor.getPassword());
        contractor.setPassword(encodedPassword);
        personService.saveContractor(contractor);
        message_notification = "Kontraktor byl úspěšně aktualizován";
        return "redirect:/validator/allContractors";
    }

    @RequestMapping(value = "/manager/deleteContractor/{id}")
    public String deleteContractor(@PathVariable(name = "id") int id) {
        worksService.deleteAllContractorsWorks(id);
        domainService.setDomainsForContractorNull(id);
        planService.deleteContractorsPlan(id);
        personService.deleteContractor(id);
        message_notification = "Kontraktor byl úspěšně smazán";
        return "redirect:/validator/allContractors";
    }

    @RequestMapping(value = "/manager/editContractor/{id}")
    public ModelAndView showEditContractorForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("manager/editContractor");
        populateWithDataEdit(mav);
        Contractor contractor = personService.getContractor(id);
        contractor.setPassword("");
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