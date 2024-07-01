package com.example.googleoidcapp.repository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserSocialLoginRecord {

    private Long id;
    private long userId;
    private String provider;
    private String subject;
}
