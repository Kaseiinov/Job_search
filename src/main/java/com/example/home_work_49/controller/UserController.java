package com.example.home_work_49.controller;

import com.example.home_work_49.dto.ResumeDto;
import com.example.home_work_49.dto.UserDto;
import com.example.home_work_49.dto.UserImageDto;
import com.example.home_work_49.dto.VacancyDto;
import com.example.home_work_49.exceptions.SuchEmailAlreadyExistsException;
import com.example.home_work_49.models.User;
import com.example.home_work_49.repository.UserImageRepository;
import com.example.home_work_49.service.ImageService;
import com.example.home_work_49.service.ResumeService;
import com.example.home_work_49.service.UserService;
import com.example.home_work_49.service.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final ImageService imageService;

    @GetMapping("profile")
    public String getProfile(Model model ,Authentication auth){
        String username = auth.getName();
        List<ResumeDto> resumes = resumeService.getResumeByUser(username);
        List<VacancyDto> vacancies = vacancyService.getVacanciesByUser(username);
        UserDto userDto = userService.getUserByEmail(username);
        if(userDto.getAccountType().equalsIgnoreCase("employer")){
            model.addAttribute("items", vacancies);
        }else if(userDto.getAccountType().equalsIgnoreCase("applicant")){
            model.addAttribute("items", resumes);
        }
        model.addAttribute("user",userDto);
        return "auth/profile";
    }

    @GetMapping("edit/{userEmail}")
    public String updateUser(Model model, @PathVariable String userEmail){
        UserDto user = userService.getUserByEmail(userEmail);
        model.addAttribute("userDto", user);
        return "auth/edit";
    }

    @PostMapping("edit")
    public String updateUser(@Valid UserDto userDto, BindingResult bindingResult, Model model) throws SuchEmailAlreadyExistsException, RoleNotFoundException {
        if(!bindingResult.hasErrors()){
            userService.updateUserByEmail(userDto.getEmail(), userDto);
            return "redirect:/users/profile";
        }
        model.addAttribute("userDto", userDto);
        return "auth/edit";
    }

}
