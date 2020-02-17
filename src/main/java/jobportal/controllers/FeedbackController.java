package jobportal.controllers;

import jobportal.models.*;
import jobportal.security.MyUser;
import jobportal.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Collection;

@Controller
public class FeedbackController {
    private String message_notification = "";
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private DomainService domainService;

    @GetMapping(value = "/manager/allFeedbacks")
    public String showAllFeedbacks(Model model){
        Collection<Feedback> feedbacks = feedbackService.findAllFeedbacks();
        model.addAttribute("feedbacksList", feedbacks);
        return "manager/allFeedbacks";
    }

    @GetMapping(value = "/allMyFeedbacks")
    public String showMyFeedbacks(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser userDetails = MyUser.class.cast(principal);
        int id = userDetails.getUserId();
        Collection<Feedback> feedbacks = feedbackService.findFeedbacksForContractor(id);
        model.addAttribute("feedbacksList", feedbacks);
        return "allMyFeedbacks";
    }

    @GetMapping(value = "/validator/allValidatorFeedbacks")
    public String showAllValidatorFeedbacks(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser userDetails = MyUser.class.cast(principal);
        int id = userDetails.getUserId();
        Collection<Feedback> feedbacks = feedbackService.findFeedbacksForValidator(id);
        model.addAttribute("feedbacksList", feedbacks);
        model.addAttribute("message_notification", message_notification);
        message_notification = "";
        return "validator/allValidatorFeedbacks";
    }

    @GetMapping(value = "/validator/newFeedback")
    public String showAddFeedbackForm(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser userDetails = MyUser.class.cast(principal);
        int id = userDetails.getUserId();
        Collection<Domain> domains = domainService.findDomainsForValidatorId(id);
        model.addAttribute("domains", domains);
        Feedback feedback = new Feedback();
        model.addAttribute("feedback", feedback);
        return "validator/addFeedback";
    }

    @RequestMapping(value = "/validator/saveFeedback", method = RequestMethod.POST)
    public String saveFeedback(@Valid @ModelAttribute("feedback") Feedback feedback, BindingResult bindingResult) {
        bindingResult.getErrorCount();
        if (bindingResult.hasErrors()) {
            return "validator/addFeedback";
        }
        feedbackService.saveFeedback(feedback);
        message_notification = "Feedback byl úspěšně uložen";
        return "redirect:/validator/allValidatorFeedbacks";
    }
}