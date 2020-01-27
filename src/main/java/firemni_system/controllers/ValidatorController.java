package firemni_system.controllers;

import firemni_system.models.Team;
import firemni_system.services.CiselnikyService;
import firemni_system.services.DomainService;
import firemni_system.services.PersonService;
import firemni_system.models.Validator;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ValidatorController {
    private String message_notification = "";
    @Autowired
    private PersonService personService;

    @Autowired
    private CiselnikyService ciselnikyService;

    @Autowired
    private DomainService domainService;

    @GetMapping(value = "/validator/allValidators")
    public String showAllValidators(Model model){
        Collection<Validator> validators = personService.findAllValidators();
        model.addAttribute("validators", validators);
        model.addAttribute("message_notification", message_notification);
        message_notification = "";
        return "validator/allValidators";
    }

    @RequestMapping(value = "/SearchValidators")
    public String showFilteredValidators(Model model, @RequestParam(name = "name", required = false)  String name){
        Collection<Validator> validators = personService.findValidatorsByFirstNameLastName(name);
        model.addAttribute("validators", validators);
        model.addAttribute("message_notification", "");
        return "validator/allValidators";
    }

    @GetMapping(value = "/manager/newValidator")
    public String showAddValidatorForm(Model model){
        populateWithData(model);
        Validator validator = new Validator();
        model.addAttribute("validator", validator);
        return "manager/addValidator";
    }

    @RequestMapping(value = "/manager/saveValidator", method = RequestMethod.POST)
    public String saveValidator(@Valid @ModelAttribute("validator") Validator validator, BindingResult bindingResult, Model model) {

        if(!personService.isUnique(validator.getLogin())){
            FieldError error = new FieldError("validator", "login",
                    "User with this login already exists");
            bindingResult.addError(error);
        }

        if (bindingResult.hasErrors()) {
            populateWithData(model);
            return "manager/addValidator";
        }
        validator.setRole("VALIDATOR");
        String encodedPassword = new BCryptPasswordEncoder().encode(validator.getPassword());
        validator.setPassword(encodedPassword);
        personService.saveValidator(validator);
        message_notification = "Nový validátor byl úspěšně přidán";
        return "redirect:/validator/allValidators";
    }

    @RequestMapping(value = "/manager/updateValidator", method = RequestMethod.POST)
    public String updateValidator(@Valid @ModelAttribute("validator") Validator validator, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            populateWithData(model);
            return "manager/editValidator";
        }
        validator.setRole("VALIDATOR");
        String encodedPassword = new BCryptPasswordEncoder().encode(validator.getPassword());
        validator.setPassword(encodedPassword);
        personService.saveValidator(validator);
        message_notification = "Validátor byl úspěšně aktualizován";
        return "redirect:/validator/allValidators";
    }

    @RequestMapping(value = "/manager/deleteValidator/{id}")
    public String deleteValidator(@PathVariable(name = "id") int id, Model model) {
        if(personService.findContractorsByMentor(id).size() > 0){

            Collection<Validator> validators = personService.findAllValidators();
            model.addAttribute("validators", validators);
            model.addAttribute("error", "Selected validator is mentor and thus cannot be deleted");
            message_notification = "";
            return "validator/allValidators";
        }
        domainService.setDomainsForValidatorNull(id);
        personService.deleteValidator(id);
        message_notification = "Validátor byl úspěšně smazán";
        return "redirect:/validator/allValidators";
    }

    @RequestMapping(value = "/manager/editValidator/{id}")
    public ModelAndView showEditValidatorForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("manager/editValidator");
        populateWithDataEdit(mav);
        Validator validator = personService.getValidator(id);
        validator.setPassword("");
        mav.addObject("validator", validator);
        return mav;
    }

    private void populateWithData(Model model){
        Collection<Team> teams = ciselnikyService.findAllTeams();
        model.addAttribute("teams",teams);
    }

    private void populateWithDataEdit(ModelAndView mav){
        Collection<Team> teams = ciselnikyService.findAllTeams();
        mav.addObject("teams", teams);
    }
}