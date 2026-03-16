package com.SE.ITHub.service.impl;

import com.SE.ITHub.dto.CreateAdminRequest;
import com.SE.ITHub.model.User;
import com.SE.ITHub.repository.AdminRepository;
import com.SE.ITHub.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository userRepository;

    public User createAdmin(CreateAdminRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName("Admin");
        user.setOauthProvider("google");
        user.setOauthProviderId("pending");
        user.setRole("admin");

        return userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}