package com.example.fileservice.controller;

import com.example.fileservice.entity.User;
import com.example.fileservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/register")
@CrossOrigin
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Check if the username already exists
        if (userService.getUserByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        User createdUser = userService.saveUser(user);
        return ResponseEntity.ok(createdUser);
    }
}
