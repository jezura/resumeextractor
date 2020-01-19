package firemni_system.controllers;

import firemni_system.models.*;
import firemni_system.security.MyUser;
import firemni_system.services.CiselnikyService;
import firemni_system.services.DomainService;
import firemni_system.services.PersonService;
import firemni_system.services.WorksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class WorksController {

    @Autowired
    private DomainService domainService;
    @Autowired
    private WorksService worksService;

    @Autowired
    private PersonService personService;
    @Autowired
    private CiselnikyService ciselnikyService;


    @GetMapping(value = "/allWorks")
    public String showAllWorks(Model model){
        Collection<Work> works = worksService.findAllWorks();
        model.addAttribute("worksList", works);
        return "allWorks";
    }

    @GetMapping(value = "/allMyWorks")
    public String showMyDomains(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser userDetails = MyUser.class.cast(principal);
        int id = userDetails.getUserId();
        Collection<Work> works = worksService.findWorksForContractor(id);
        double hoursMonthly = worksService.hoursSum(works);
        model.addAttribute("works", works);
        return "allMyWorks";
    }

    @GetMapping(value = "/newWork")
    public String showAddWorkForm(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser userDetails = MyUser.class.cast(principal);
        int id = userDetails.getUserId();
        Work work = new Work();
        Collection<Domain> domains = domainService.findDomainsForContractorId(id);
        Collection<Team> teams = ciselnikyService.findAllTeams();
        Collection<WorkType> workTypes = ciselnikyService.findAllWorkTypes();
        model.addAttribute("teams",teams);
        model.addAttribute("workTypes", workTypes);
        model.addAttribute("domains", domains);
        model.addAttribute("work", work);
        return "addWork";
    }

    @RequestMapping(value = "/saveWork", method = RequestMethod.POST)
    public String saveWork(@ModelAttribute("work") Work work) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser userDetails = MyUser.class.cast(principal);
        int id = userDetails.getUserId();
        Contractor contractor = personService.getContractor(id);
        work.setContractor(contractor);
        worksService.saveWork(work);
        return "redirect:/newWork";
    }

    @RequestMapping(value = "/deleteWork/{id}")
    public String deleteWork(@PathVariable(name = "id") int id) {
        worksService.deleteWork(id);
        return "redirect:/allMyWorks";
    }

}