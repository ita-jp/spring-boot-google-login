package com.example.googleoidcapp.controller;

import com.example.googleoidcapp.config.SocialLoginSessionData;
import com.example.googleoidcapp.service.LoggedInUser;
import com.example.googleoidcapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;

    @GetMapping("/")
    public String index(@AuthenticationPrincipal LoggedInUser user, Model model) {
        model.addAttribute("loggedInUser", user);
        return "index";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/logout")
    public String showLogout() {
        return "logout";
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
        var socialLogin = SocialLoginSessionData.from(session);
        if (socialLogin == null) {
            return "redirect:/register";
        }

        userService.registerUserWithSocialLogin(username, socialLogin.provider(), socialLogin.subject());
        return "redirect:/oauth2/authorization/" + socialLogin.provider();
    }
}
