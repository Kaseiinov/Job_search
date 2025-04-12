package com.example.home_work_49.controller.api;

import com.example.home_work_49.dto.UserDto;
import com.example.home_work_49.exceptions.SuchEmailAlreadyExistsException;
import com.example.home_work_49.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("restProfile")
@RequestMapping("api/users")
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

    @PostMapping("createUser")
    public HttpStatus registerUser(@RequestBody @Valid UserDto userDto) throws SuchEmailAlreadyExistsException {
        log.info("Creating user: {}", userDto.getEmail());
        userService.addUser(userDto);
        return HttpStatus.CREATED;
    }

    @PutMapping("update/{userName}")
    public HttpStatus updateUserByName(@PathVariable("userName") @Valid String userName,  @RequestBody UserDto userDto) throws SuchEmailAlreadyExistsException {
        log.warn("Updating user: {}", userDto.getEmail());
        userService.updateUserByEmail(userName, userDto);
        return HttpStatus.OK;
    }
}
