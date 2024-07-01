package com.example.googleoidcapp.config;

import com.example.googleoidcapp.repository.UserSocialLoginRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserSocialLoginRepository userSocialLoginRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        var oidcUser = (OidcUser) authentication.getPrincipal();
        var subject = oidcUser.getSubject();

        if (isRegisteredUser(subject)) {
            response.sendRedirect("/");
            return;
        }

        request.getSession().setAttribute("subject", subject);
        request.getSession().setAttribute("provider", "google");
        response.sendRedirect("/register-profile");
    }

    private boolean isRegisteredUser(String subject) {
        return userSocialLoginRepository.selectBySubject(subject).isPresent();
    }
}