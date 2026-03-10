package com.example.ecommerce.controller;

import com.example.ecommerce.model.User;
import com.example.ecommerce.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    private final UserService service;

    public AuthController(UserService service){
        this.service = service;
    }

    @GetMapping("/")
    public String start(){
        return "redirect:/signup";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model){
        model.addAttribute("user",new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user,
                         @RequestParam("confirmPassword") String confirmPassword,
                         Model model){

        if(!user.getPassword().equals(confirmPassword)){
            model.addAttribute("error","Passwords do not match");
            return "signup";
        }

        if(service.emailExists(user.getEmail())){
            model.addAttribute("error","Email already registered");
            return "signup";
        }

        service.register(user);
        return "redirect:/login";
    }

@PostMapping("/login")
public String login(@RequestParam String email,
                    @RequestParam String password,
                    HttpSession session,
                    Model model){

    User user = service.login(email,password);

    if(user == null){
        model.addAttribute("error","Invalid email or password");
        return "login";
    }

    session.setAttribute("username",user.getName());
    return "redirect:/home";
}

    @GetMapping("/logout")
    public String logout(HttpSession session){

        session.invalidate();

        return "redirect:/login";
    }

}