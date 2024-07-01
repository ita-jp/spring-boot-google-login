package com.example.googleoidcapp.repository;

public record UserSocialLogin(
        Long id,
        long userId,
        String subject
) {
}
