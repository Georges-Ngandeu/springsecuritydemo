package com.playground.securitydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @GetMapping("/")
    public String homePage() { return "welcome";
    }

    @GetMapping("/welcome")
    public String welcomePage() {
        return "welcome";
    }

    @GetMapping ("/authenticated")
    public String AuthenticatedPage() {
        return "authenticated";
    }

    @GetMapping("/login")
    public String loginPage() { return "login"; }

    @GetMapping ("/logout")
    public String logoutPage() {
        return "redirect:/welcome";
    }

}