package firemni_system.controllers;
import firemni_system.models.Plan;
import firemni_system.security.MyUser;
import firemni_system.services.PersonService;
import firemni_system.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class PlanController {
    private String message_notification = "";
    @Autowired
    private PlanService planService;
    @Autowired
    private PersonService personService;

    @GetMapping(value = "/validator/allWorkingPlans")
    public String showAllWorkingPlans(Model model){
        Collection<Plan> plans = planService.findAllPlans();
        model.addAttribute("plansList", plans);
        return "validator/allWorkingPlans";
    }

    @GetMapping(value = "/myWorkingPlan")
    public String showMyWorkingPlanContractor(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser userDetails = MyUser.class.cast(principal);
        int id = userDetails.getUserId();
        Plan plan = planService.findPlanForContractorId(id);
        model.addAttribute("plan", plan);
        model.addAttribute("message_notification", message_notification);
        message_notification = "";
        return "myWorkingPlan";
    }

    @RequestMapping(value = "/saveWorkingPlan", method = RequestMethod.POST)
    public String savePlan(@ModelAttribute("plan") Plan plan, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editWorkingPlan";
        }
        planService.savePlan(plan);
        message_notification = "Pracovní plán byl úspěšně aktualizován";
        return "redirect:/myWorkingPlan";
    }

    @RequestMapping(value = "/editWorkingPlan/{id}")
    public ModelAndView showEditPlanForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("editWorkingPlan");
        Plan plan = planService.getPlanById(id);
        mav.addObject("plan", plan);
        return mav;
    }
}