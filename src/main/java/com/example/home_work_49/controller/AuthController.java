package com.example.home_work_49.controller;

import com.example.home_work_49.dto.UserDto;
import com.example.home_work_49.exceptions.SuchEmailAlreadyExistsException;
import com.example.home_work_49.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("register")
    public String register(Model model){
        return "auth/register";
    }

    @PostMapping("register")
    public String register(@Valid UserDto userDto) throws SuchEmailAlreadyExistsException {
        userService.addUser(userDto);
        return "redirect:/";
    }

    @GetMapping("login")
    public String login(Model model){
        return "auth/login";
    }

//    @PostMapping("login")
//    public String login(@Valid UserDto userDto) {
//        userService.getUserByEmail()
//        return "redirect:/";
//    }


}
