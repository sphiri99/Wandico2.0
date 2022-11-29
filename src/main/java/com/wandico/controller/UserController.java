package com.wandico.controller;

import com.wandico.entity.UserDetails;
import com.wandico.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    private LoginService userService;


    @GetMapping("/registration")
    public String registration(Model model) {

        model.addAttribute("userForm", new UserDetails());

        return "registration";
    }

    @PostMapping("registration")
    public String registration(@ModelAttribute("userForm") UserDetails userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.saveUserDetails(userForm);

       // securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:welcome";
    }


    @GetMapping("/login")
    public String login(Model model,@ModelAttribute("userForm") UserDetails userForm) {

        model.addAttribute("userForm", userForm);

        if(userService.canLogin(userForm.getUsername(),userForm.getPassword())){
            return "redirect:welcome";

        }

        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("userForm") UserDetails userForm, BindingResult bindingResult) {
       if (bindingResult.hasErrors()) {
            return "login";
        }

        if(userService.canLogin(userForm.getUsername(),userForm.getPassword())){
            return "redirect:welcome";

        }

        return "login";
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.POST)
    public ModelAndView welcome(Model model) {
        return new ModelAndView("welcome");
    }
}
