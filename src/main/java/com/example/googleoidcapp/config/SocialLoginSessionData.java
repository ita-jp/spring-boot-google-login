package com.example.googleoidcapp.config;

import jakarta.servlet.http.HttpSession;

public record SocialLoginSessionData(
        String provider,
        String subject
) {

    public static final String SESSION_ATTRIBUTE_NAME = "SOCIAL_LOGIN_SESSION_DATA";

    public static SocialLoginSessionData from(HttpSession session) {
        return (SocialLoginSessionData) session.getAttribute(SESSION_ATTRIBUTE_NAME);
    }

    public boolean isBlankAny() {
        return provider.isBlank() || subject.isBlank();
    }
}
