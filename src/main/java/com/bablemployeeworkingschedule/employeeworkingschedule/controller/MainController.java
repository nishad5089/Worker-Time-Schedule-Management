package com.bablemployeeworkingschedule.employeeworkingschedule.controller;


import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

@Controller
public class MainController {
    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }
    @GetMapping("/")
    public String root() {
        return "redirect:/employee/";
    }
    @GetMapping("/login")
    public String login(Model model) {
        String message = (String)model.asMap().get("message");
        model.addAttribute("message",message);
        if (!isLogged()) return "login";
        return "redirect:/employee/";
    }
    public static boolean isLogged() {
       return SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails;
    }

}