package com.example.fileservice.service;

import com.example.fileservice.entity.User;

import java.util.Optional;

public interface UserService {
    User getUserByEmail(String email);
    Optional<User> getUserById(Long id);
    User saveUser(User user);
    void deleteUser(User user);
}
