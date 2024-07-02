package com.example.googleoidcapp.service;

import com.example.googleoidcapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        var oidcUser = super.loadUser(userRequest);
        return userRepository.selectBySubject(oidcUser.getSubject())
                .map(r -> (OidcUser) new LoggedInUser(oidcUser, r.getUsername()))
                .orElse(oidcUser); // 会員未登録の場合は、oidcUser を返して会員登録フローを続ける
    }
}
