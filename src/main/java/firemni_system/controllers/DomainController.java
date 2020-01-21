package firemni_system.controllers;
import firemni_system.models.Contractor;
import firemni_system.models.Domain;
import firemni_system.models.Validator;
import firemni_system.security.MyUser;
import firemni_system.services.DomainService;
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
public class DomainController {
    @Autowired
    private DomainService domainService;
    @Autowired
    private PersonService personService;

    @GetMapping(value = "/validator/allDomains")
    public String showAllDomains(Model model){
        Collection<Domain> domains = domainService.findAllDomains();
        model.addAttribute("domainsList", domains);
        return "validator/allDomains";
    }

    @GetMapping(value = "/validator/allValidatorDomains")
    public String showMyDomainsValidator(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser userDetails = MyUser.class.cast(principal);
        int id = userDetails.getUserId();
        Collection<Domain> domains = domainService.findDomainsForValidatorId(id);
        model.addAttribute("domainsList", domains);
        return "validator/allValidatorDomains";
    }

    @GetMapping(value = "/allMyDomains")
    public String showMyDomainsContractor(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser userDetails = MyUser.class.cast(principal);
        int id = userDetails.getUserId();
        Collection<Domain> domains = domainService.findDomainsForContractorId(id);
        model.addAttribute("domainsList", domains);
        return "allMyDomains";
    }

    @GetMapping(value = "/validator/newDomain")
    public String showAddDomainForm(Model model){
        Domain domain = new Domain();
        model.addAttribute("domain", domain);
        Collection<Validator> validators = personService.findAllValidators();
        model.addAttribute("validators", validators);
        return "validator/addDomain";
    }

    @RequestMapping(value = "/validator/saveDomain", method = RequestMethod.POST)
    public String saveDomain(@Valid @ModelAttribute("domain") Domain domain, BindingResult bindingResult) {

        bindingResult.getErrorCount();
        if (bindingResult.hasErrors()) {
            return "validator/addDomain";
        }

        domainService.saveDomain(domain);
        return "redirect:/validator/allDomains";
    }

    @RequestMapping(value = "/validator/deleteDomain/{id}")
    public String deleteDomain(@PathVariable(name = "id") int id) {
        domainService.deleteDomain(id);
        return "redirect:/validator/allDomains";
    }

    @RequestMapping(value = "/validator/editDomain/{id}")
    public ModelAndView showEditDomainForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("validator/editDomain");
        Collection<Validator> validators = personService.findAllValidators();
        mav.addObject("validators", validators);
        Collection<Contractor> contractors = personService.findAllContractors();
        mav.addObject("contractors", contractors);
        Domain domain = domainService.getDomain(id);
        mav.addObject("domain", domain);
        return mav;
    }
}