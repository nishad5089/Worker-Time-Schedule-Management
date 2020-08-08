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




//redirect attribute
//    @RequestMapping(value = "test/{id}")
//    public String handleTestRequest (@PathVariable("id") String id, Model model,
//                                     RedirectAttributes ra) {
//        if (!id.matches("\\d+")) {
//            model.addAttribute("msg", "id should only have digits");
//            return "error-page";
//        } else {
//            ra.addAttribute("attr", "attrVal");
//            ra.addFlashAttribute("flashAttr", "flashAttrVal");
//            return "redirect:/test2/{id}";
//        }
//    }
//
//    @RequestMapping("test2/{id}")
//    public String handleRequest (@PathVariable("id") String id,
//                                 @RequestParam("attr") String attr,
//                                 @ModelAttribute("flashAttr") String flashAttr,
//                                 Model model) {
//
//        model.addAttribute("id", id);
//        model.addAttribute("attr", attr);
//        model.addAttribute("flashAttr", flashAttr+"");
//        return "my-page";
//    }
}