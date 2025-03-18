package com.example.home_work_49.controller;

import com.example.home_work_49.models.User;
import com.example.home_work_49.models.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        return ResponseEntity.ok("User registered as " + user.getAccountType());
    }
}
