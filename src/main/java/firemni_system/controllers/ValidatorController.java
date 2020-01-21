package firemni_system.controllers;

import firemni_system.models.SwimlaneType;
import firemni_system.models.Team;
import firemni_system.services.CiselnikyService;
import firemni_system.services.PersonService;
import firemni_system.models.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;

@Controller
public class ValidatorController {
    @Autowired
    private PersonService personService;

    @Autowired
    private CiselnikyService ciselnikyService;


    @GetMapping(value = "/validator/allValidators")
    public String showAllValidators(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<Validator> validators = personService.findAllValidators();
        model.addAttribute("validatorsList", validators);
        model.addAttribute("login", authentication);
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
        bindingResult.getErrorCount();
        if (bindingResult.hasErrors()) {
            populateWithData(model);
            return "manager/addValidator";
        }
        validator.setRole("VALIDATOR");
        personService.saveValidator(validator);
        return "redirect:/validator/allValidators";
    }

    @RequestMapping(value = "/manager/deleteValidator/{id}")
    public String deleteValidator(@PathVariable(name = "id") int id) {
        personService.deleteValidator(id);
        return "redirect:/validator/allValidators";
    }

    @RequestMapping(value = "/manager/editValidator/{id}")
    public ModelAndView showEditValidatorForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("manager/editValidator");
        Validator validator = personService.getValidator(id);
        mav.addObject("validator", validator);
        return mav;
    }


    private void populateWithData(Model model){
        Collection<Team> teams = ciselnikyService.findAllTeams();
        model.addAttribute("teams",teams);
    }
}