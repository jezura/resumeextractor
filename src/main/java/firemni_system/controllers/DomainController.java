package firemni_system.controllers;
import firemni_system.models.*;
import firemni_system.security.MyUser;
import firemni_system.services.DomainService;
import firemni_system.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.Collection;

@Controller
public class DomainController {
    private String message_notification = "";
    @Autowired
    private DomainService domainService;
    @Autowired
    private PersonService personService;

    @GetMapping(value = "/allDomains")
    public String showAllDomains(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser userDetails = MyUser.class.cast(principal);
        int id = userDetails.getUserId();
        Collection<Domain> domains = domainService.findAllDomains();
        Collection<Contractor> contractors = personService.findAllContractors();
        Collection<Validator> validators = personService.findAllValidators();

        if(userDetails.getRole().equals("VALIDATOR")){
           contractors = personService.findContractorsByMentor(id);
           domains = domainService.findDomainsForValidatorId(id);
        }
      else if(userDetails.getRole().equals("CONTRACTOR")){
            domains = domainService.findDomainsForContractorId(id);
        }

        model.addAttribute("validators", validators);
        model.addAttribute("contractors", contractors);
        model.addAttribute("domains", domains);
        model.addAttribute("message_notification", message_notification);
        message_notification = "";
        return "allDomains";
    }

    @RequestMapping(value = "/SearchDomains")
    public String showFilteredDomains(Model model,
                                      @RequestParam(name = "name", required = false) String name,
                                      @RequestParam(name = "contractor_id", required = false)  Integer contractorId,
                                      @RequestParam(name = "validator_id", required = false)  Integer validatorId){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser userDetails = MyUser.class.cast(principal);
        int id = userDetails.getUserId();
        Collection<Contractor> contractors;
        contractors = personService.findAllContractors();
        Collection<Validator> validators = personService.findAllValidators();
        Collection<Domain> domains;
        if(contractorId == null) contractorId = 0;
        if(validatorId == null) validatorId = 0;
         domains = domainService.findAllDomains();
        if(!name.isBlank()) {
            domains = domainService.findDomainsByName(name);
            model.addAttribute("searchedDomain",name);
        }
        if(userDetails.getRole().equals("VALIDATOR")){
            contractors = personService.findContractorsByMentor(id);
            domains = domainService.filterByValidatorID(id, domains);
        }
        else if(userDetails.getRole().equals("CONTRACTOR")){
            domains = domainService.filterByContractorID(id,domains);
        }
        if (contractorId != 0) {
            domains = domainService.filterByContractorID(contractorId, domains);
            model.addAttribute("selectedContractor",contractorId);
        }
        if (validatorId != 0) {
            domains = domainService.filterByValidatorID(validatorId, domains);
            model.addAttribute("selectedValidator",validatorId);
        }

        model.addAttribute("validators", validators);
        model.addAttribute("contractors", contractors);
        model.addAttribute("domains", domains);
        model.addAttribute("message_notification", "");
        return "allDomains";
    }

    @GetMapping(value = "/validator/newDomain")
    public String showAddDomainForm(Model model){
        Domain domain = new Domain();
        model.addAttribute("domain", domain);
  populateWithData(model);
        return "validator/addDomain";
    }

    @RequestMapping(value = "/validator/saveDomain", method = RequestMethod.POST)
    public String saveDomain(@Valid @ModelAttribute("domain") Domain domain, BindingResult bindingResult, Model model) {
        if(!domainService.isUnique(domain.getName())){
            FieldError error = new FieldError("addDomain", "name",
                    "Domain name already exists");
            bindingResult.addError(error);
        }

        if (bindingResult.hasErrors()) {
            populateWithData(model);
            return "validator/addDomain";
        }

        domainService.saveDomain(domain);
        message_notification = "Nová doména byla úspěšně přidána";
        return "redirect:/allDomains";
    }

    @RequestMapping(value = "/validator/updateDomain", method = RequestMethod.POST)
    public String updateDomain(@Valid @ModelAttribute("domain") Domain domain, BindingResult bindingResult, Model model ) {

        if (bindingResult.hasErrors()) {
            populateWithData(model);
            return "validator/editDomain";
        }
        domainService.saveDomain(domain);
        message_notification = "Doména byla úspěšně aktualizována";
        return "redirect:/allDomains";
    }

    @RequestMapping(value = "/validator/deleteDomain/{id}")
    public String deleteDomain(@PathVariable(name = "id") int id) {
        domainService.deleteDomain(id);
        message_notification = "Nová doména byla úspěšně smazána";
        return "redirect:/allDomains";
    }

    @RequestMapping(value = "/validator/editDomain/{id}")
    public ModelAndView showEditDomainForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("validator/editDomain");
        populateWithDataEdit(mav);
        Domain domain = domainService.getDomain(id);
        mav.addObject("domain", domain);
        return mav;
    }


    private void populateWithData(Model model){
        Collection<Validator> validators = personService.findAllValidators();
        Collection<Contractor> contractors = personService.findAllContractors();
        model.addAttribute("contractors", contractors);
        model.addAttribute("validators", validators);
    }

    private void populateWithDataEdit(ModelAndView mav){
        Collection<Validator> validators = personService.findAllValidators();
        Collection<Contractor> contractors = personService.findAllContractors();
        mav.addObject("contractors", contractors);
        mav.addObject("validators", validators);
    }
}