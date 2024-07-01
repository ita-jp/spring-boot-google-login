package com.example.googleoidcapp.service;

import com.example.googleoidcapp.repository.UserRecord;
import com.example.googleoidcapp.repository.UserRepository;
import com.example.googleoidcapp.repository.UserSocialLoginRecord;
import com.example.googleoidcapp.repository.UserSocialLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSocialLoginRepository userSocialLoginRepository;

    @Transactional
    public void registerUserWithSocialLogin(String username, String provider, String subject) {
        var newUser = new UserRecord(null, username);
        userRepository.insert(newUser);

        var newUserSocialLogin = new UserSocialLoginRecord(null, newUser.getId(), provider, subject);
        userSocialLoginRepository.insert(newUserSocialLogin);
    }
}
