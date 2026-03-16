package com.SE.ITHub.service;

import com.SE.ITHub.dto.CreateAdminRequest;
import com.SE.ITHub.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface AdminService {
    User createAdmin(CreateAdminRequest request);
    void deleteUser(UUID id);
    List<User> getAllUsers();
}