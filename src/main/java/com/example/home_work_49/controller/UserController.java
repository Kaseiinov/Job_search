package com.example.home_work_49.controller;

import com.example.home_work_49.dto.UserDto;
import com.example.home_work_49.models.User;
import com.example.home_work_49.models.Vacancy;
import com.example.home_work_49.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("{userName}")
    public UserDto getUserByName(@PathVariable String userName) {
        String capUserName = StringUtils.capitalize(userName.toLowerCase());
        return userService.getUserByName(capUserName);
    }

    @PostMapping
    public HttpStatus registerUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
        return HttpStatus.CREATED;
    }
}
