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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;

@Controller
public class WorksController {
    private String message_notification = "";
    @Autowired
    private DomainService domainService;
    @Autowired
    private WorksService worksService;
    @Autowired
    private PersonService personService;
    @Autowired
    private CiselnikyService ciselnikyService;

    @GetMapping(value = "/allWorks")
    public String showMyWorks(Model model){
        Collection<Contractor> contractors = personService.findAllContractors();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser userDetails = MyUser.class.cast(principal);
        int id = userDetails.getUserId();
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        Collection<Work> works = worksService.findAllWorks();
        if(userDetails.getRole().equals("CONTRACTOR")) {
            works = worksService.findWorksForContractor(id);
        }
        if(userDetails.getRole().equals("VALIDATOR")) {
            contractors = personService.findContractorsByMentor(id);
            works = worksService.filterByMentorId(id, works);
        }
        double hoursMonthly = worksService.hoursByYearAndMonth(works,year, month);
        model.addAttribute("works", works);
        model.addAttribute("contractors", contractors);
        model.addAttribute("year", year);
        model.addAttribute("month", String.format("%02d", month));
        model.addAttribute("localDate", LocalDate.now());
        model.addAttribute("role", userDetails.getRole());
        model.addAttribute("hours_monthly", hoursMonthly);
        model.addAttribute("message_notification", message_notification);
        message_notification = "";
        return "allWorks";
    }

    @RequestMapping(value = "/SearchWorks")
    public String showFilteredWorks(Model model,
                                    @RequestParam(name = "date", required = false)  String date,
                                    @RequestParam(name = "contractor_id", required = false)  Integer contractorId){
        Collection<Contractor> contractors = personService.findAllContractors();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser userDetails = MyUser.class.cast(principal);
        Collection<Work> works;
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();

        if(contractorId == null) contractorId = 0;

        if(!date.isBlank()) {
            String[] parts = date.split("-");
            year=Integer.parseInt(parts[0]);
            month=Integer.parseInt(parts[1]);
            works =worksService.filtrateWorksByYearAndMonth(year, month);
        }
        else{
            works =worksService.findAllWorks();
        }

            if (contractorId != 0) {
                works = worksService.filterByUserID(contractorId, works);
                model.addAttribute("selectedContractor",contractorId);
            }

        if(userDetails.getRole().equals("CONTRACTOR")){

            works = worksService.filterByUserID(userDetails.getUserId(), works);
        }
        if(userDetails.getRole().equals("VALIDATOR")) {
            contractors = personService.findContractorsByMentor(userDetails.getUserId());
            works = worksService.filterByMentorId(userDetails.getUserId(), works);
        }

        double hoursMonthly = worksService.hoursByYearAndMonth(works,year, month);
        model.addAttribute("works", works);
        model.addAttribute("contractors", contractors);
        model.addAttribute("year", year);
        model.addAttribute("month", String.format("%02d", month));
        model.addAttribute("localDate", LocalDate.now());
        model.addAttribute("role", userDetails.getRole());
        model.addAttribute("hours_monthly", hoursMonthly);
        model.addAttribute("message_notification", "");
        return "allWorks";
    }

    @GetMapping(value = "/newWork")
    public String showAddWorkForm(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser userDetails = MyUser.class.cast(principal);
        int id = userDetails.getUserId();

        Work work = new Work();
        model.addAttribute("work", work);
        popluateWithData(id, model);
        return "addWork";
    }

    @RequestMapping(value = "/saveWork", method = RequestMethod.POST)
    public String saveWork(@Valid @ModelAttribute("work") Work work, BindingResult bindingResult, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser userDetails = MyUser.class.cast(principal);
        int id = userDetails.getUserId();
        Contractor contractor = personService.getContractor(id);
        work.setContractor(contractor);
        if (bindingResult.getErrorCount()>1) {
                popluateWithData(id, model);
                return "addWork";
        }
        worksService.saveWork(work);
        message_notification = "Záznam o práci byl úspěšně uložen";
        return "redirect:/allWorks";
    }

    @RequestMapping(value = "/manager/deleteWork/{id}")
    public String deleteWork(@PathVariable(name = "id") int id) {
        worksService.deleteWork(id);
        message_notification = "Záznam o práci byl úspěšně smazán";
        return "redirect:/allWorks";
    }

    private void popluateWithData(int id, Model model){
        Collection<Domain> domains = domainService.findDomainsForContractorId(id);
        Collection<Team> teams = ciselnikyService.findAllTeams();
        Collection<WorkType> workTypes = ciselnikyService.findAllWorkTypes();
        model.addAttribute("teams",teams);
        model.addAttribute("workTypes", workTypes);
        model.addAttribute("domains", domains);
    }
}