package com.example.googleoidcapp.config;

import com.example.googleoidcapp.repository.UserSocialLoginRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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
        String clientRegistrationId = "";
        if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {
            clientRegistrationId = oauth2Token.getAuthorizedClientRegistrationId();
        }

        String subject = "";
        if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            subject = oidcUser.getSubject();
        }

        if (Strings.isBlank(clientRegistrationId) || Strings.isBlank(subject)) {
            response.sendRedirect("/error");
            return;
        }

        if (isRegisteredUser(subject)) {
            response.sendRedirect("/");
            return;
        }

        request.getSession().setAttribute(
                SocialLoginSessionData.SESSION_ATTRIBUTE_NAME,
                new SocialLoginSessionData(clientRegistrationId, subject)
        );

        response.sendRedirect("/register-profile");
    }

    private boolean isRegisteredUser(String subject) {
        return userSocialLoginRepository.selectBySubject(subject).isPresent();
    }
}