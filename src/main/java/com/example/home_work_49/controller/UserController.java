package com.example.home_work_49.controller;

import com.example.home_work_49.dto.UserDto;
import com.example.home_work_49.models.User;
import com.example.home_work_49.models.Vacancy;
import com.example.home_work_49.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public HttpStatus registerUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
        return HttpStatus.CREATED;
    }
}
