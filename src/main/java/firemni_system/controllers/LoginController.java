package firemni_system.controllers;

import firemni_system.models.Login;
import firemni_system.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping(value = "/allLogins")
    public String showAllLogins(Model model){
        Collection<Login> logins = loginService.findAllLogins();
        model.addAttribute("loginsList", logins);
        return "allLogins";
    }

    @GetMapping(value = "/newLogin")
    public String showAddContractorForm(Model model){
        Login login = new Login();
        model.addAttribute("login", login);
        return "addLogin";
    }

    @RequestMapping(value = "/saveLogin", method = RequestMethod.POST)
    public String saveLogin(@ModelAttribute("login") Login login) {
        loginService.saveLogin(login);
        return "redirect:/allLogins";
    }

    @RequestMapping(value = "/Login/{id}")
    public String deleteLogin(@PathVariable(name = "id") int id) {
        loginService.deleteLogin(id);
        return "redirect:/allLogins";
    }

    @RequestMapping(value = "/editLogin/{id}")
    public ModelAndView showEditLoginForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("editLogin");
        Login login = loginService.getLogin(id);
        mav.addObject("login", login);
        return mav;
    }
}