package firemni_system.controllers;

import firemni_system.services.PersonService;
import firemni_system.workers.Contractor;
import firemni_system.workers.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class ValidatorController {
    @Autowired
    private PersonService personService;


    @GetMapping(value = "/allValidators")
    public String showAllValidators(Model model){
        Collection<Validator> validators = personService.findAllValidators();
        model.addAttribute("validatorsList", validators);
        return "allValidators";
    }

    @GetMapping(value = "/newValidator")
    public String showAddValidatorForm(Model model){
        Validator validator = new Validator();
        model.addAttribute("validator", validator);
        return "addValidator";
    }

    @RequestMapping(value = "/saveValidator", method = RequestMethod.POST)
    public String saveValidator(@ModelAttribute("validator") Validator validator) {
        personService.saveValidator(validator);
        return "redirect:/allValidators";
    }

    @RequestMapping(value = "/deleteValidator/{id}")
    public String deleteValidator(@PathVariable(name = "id") int id) {
        personService.deleteValidator(id);
        return "redirect:/allValidators";
    }

    @RequestMapping(value = "/editValidator/{id}")
    public ModelAndView showEditValidatorForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("editValidator");
        Validator validator = personService.getValidator(id);
        mav.addObject("validator", validator);
        return mav;
    }
}