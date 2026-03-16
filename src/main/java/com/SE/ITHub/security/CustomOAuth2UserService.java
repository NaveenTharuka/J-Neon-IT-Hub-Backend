package com.SE.ITHub.security;

import com.SE.ITHub.model.User;
import com.SE.ITHub.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private AdminRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        OAuth2User oauthUser = super.loadUser(userRequest);

        String email = oauthUser.getAttribute("email");

        // print login attempt
        System.out.println("OAuth login attempt from email: " + email);

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {

            System.out.println("Email not registered: " + email);

            throw new OAuth2AuthenticationException(
                    new OAuth2Error("unauthorized_user"),
                    "User not registered"
            );
        }

        System.out.println("Authorized user: " + email);

        return oauthUser;
    }
}