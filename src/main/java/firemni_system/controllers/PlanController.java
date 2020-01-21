package firemni_system.controllers;
import firemni_system.models.Contractor;
import firemni_system.models.Domain;
import firemni_system.models.Plan;
import firemni_system.models.Validator;
import firemni_system.security.MyUser;
import firemni_system.services.DomainService;
import firemni_system.services.PersonService;
import firemni_system.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class PlanController {
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
        return "myWorkingPlan";
    }

    @RequestMapping(value = "/saveWorkingPlan", method = RequestMethod.POST)
    public String savePlan(@ModelAttribute("plan") Plan plan) {
        planService.savePlan(plan);
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