package firemni_system.controllers;

import firemni_system.models.*;
import firemni_system.security.MyUser;
import firemni_system.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping(value = "/manager/allFeedbacks")
    public String showAllFeedbacks(Model model){
        Collection<Feedback> feedbacks = feedbackService.findAllFeedbacks();
        model.addAttribute("feedbacksList", feedbacks);
        return "manager/allFeedbacks";
    }

    @GetMapping(value = "/allMyFeedbacks")
    public String showMyFeedBacks(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser userDetails = MyUser.class.cast(principal);
        int id = userDetails.getUserId();
        Collection<Feedback> feedbacks = feedbackService.findFeedbacksForContractor(id);
        model.addAttribute("feedbacksList", feedbacks);
        return "allMyFeedbacks";
    }

    /*
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
        return "redirect:/allMyWorks";
    }

    @RequestMapping(value = "/deleteWork/{id}")
    public String deleteWork(@PathVariable(name = "id") int id) {
        worksService.deleteWork(id);
        return "redirect:/allMyWorks";
    }*/

}