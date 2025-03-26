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

import java.util.List;


@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("byName/{userName}")
    public UserDto getUserByName(@PathVariable String userName) {
        return userService.getUserByName(userName);
    }

    @GetMapping("byVacancy/{vacancyName}")
    public List<UserDto> getApplicantsByVacancy(@PathVariable String vacancyName) {
        return userService.getApplicantsByVacancy(vacancyName);
    }

    @GetMapping("byPhone/{userPhone}")
    public UserDto getUserByPhone(@PathVariable String userPhone) {
        return userService.getUserByPhone(userPhone);
    }

    @GetMapping("byEmail/{userEmail}")
    public UserDto getUserByEmail(@PathVariable String userEmail) {
        return userService.getUserByEmail(userEmail);
    }

    @PostMapping
    public HttpStatus registerUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
        return HttpStatus.CREATED;
    }
}
