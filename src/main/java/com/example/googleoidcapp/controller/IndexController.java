package com.example.googleoidcapp.controller;

import com.example.googleoidcapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistration() {
        return "register";
    }

    @GetMapping("/register-profile")
    public String showProfileRegistration() {
        return "register-profile";
    }

    @PostMapping("/register-profile")
    public String register(HttpSession session, String username) {
        var subject = (String) session.getAttribute("subject");
        var provider = (String) session.getAttribute("provider");

        if (subject == null || provider == null) {
            return "redirect:/register";
        }

        userService.registerUserWithSocialLogin(username, provider, subject);

        return "redirect:/login";
    }
}
