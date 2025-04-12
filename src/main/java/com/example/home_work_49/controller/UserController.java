package com.example.home_work_49.controller;

import com.example.home_work_49.dto.UserDto;
import com.example.home_work_49.exceptions.SuchEmailAlreadyExistsException;
import com.example.home_work_49.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("profile")
    public String getApplicantProfile(Model model ,Authentication auth){
        String username = auth.getName();
        UserDto userDto = userService.getUserByEmail(username);
        model.addAttribute("user",userDto);
        return "auth/profile";
    }

    @GetMapping("update")
    public String updateUser(Model model ,Authentication auth){
        return "auth/edit";
    }

    @PostMapping("update")
    public String updateUser( UserDto userDto, Authentication auth) throws SuchEmailAlreadyExistsException {
        userService.updateUserByEmail(auth.getName(), userDto);
        return "redirect:/users/profile";
    }

}
