package firemni_system.controllers;
import firemni_system.models.Domain;
import firemni_system.models.Validator;
import firemni_system.services.DomainService;
import firemni_system.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class DomainController {
    @Autowired
    private DomainService domainService;
    @Autowired
    private PersonService personService;

    @GetMapping(value = "/allDomains")
    public String showAllDomains(Model model){
        Collection<Domain> domains = domainService.findAllDomains();
        model.addAttribute("domainsList", domains);
        return "allDomains";
    }

    @GetMapping(value = "/newDomain")
    public String showAddDomainForm(Model model){
        Domain domain = new Domain();
        model.addAttribute("domain", domain);
        Collection<Validator> validators = personService.findAllValidators();
        model.addAttribute("validators", validators);
        return "addDomain";
    }

    @RequestMapping(value = "/saveDomain", method = RequestMethod.POST)
    public String saveDomain(@ModelAttribute("domain") Domain domain) {
        domainService.saveLogin(domain);
        return "redirect:/allDomains";
    }

    @RequestMapping(value = "/deleteDomain/{id}")
    public String deleteDomain(@PathVariable(name = "id") int id) {
        domainService.deleteDomain(id);
        return "redirect:/allDomains";
    }

    @RequestMapping(value = "/editDomain/{id}")
    public ModelAndView showEditDomainForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("editDomain");
        Domain domain = domainService.getLogin(id);
        mav.addObject("domain", domain);
        return mav;
    }
}