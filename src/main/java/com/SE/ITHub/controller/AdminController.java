package com.SE.ITHub.controller;

import com.SE.ITHub.dto.CreateAdminRequest;
import com.SE.ITHub.model.User;
import com.SE.ITHub.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    @GetMapping("/users")
    public List<User> user(@AuthenticationPrincipal OAuth2User principal) {
        return adminService.getAllUsers();
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

    @PostMapping("/create")
    public User createAdmin(@RequestBody CreateAdminRequest request) {
        return adminService.createAdmin(request);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable UUID id) {
        adminService.deleteUser(id);
        return "User deleted successfully";
    }
}