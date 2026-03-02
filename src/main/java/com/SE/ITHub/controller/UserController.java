package com.SE.ITHub.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return Map.of("error", "User not authenticated");
        }
        return principal.getAttributes();
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return "Please log in first.";
        }
        String name = principal.getAttribute("name");
        String email = principal.getAttribute("email");
        return "Name: " + name + ", Email: " + email;
    }
}