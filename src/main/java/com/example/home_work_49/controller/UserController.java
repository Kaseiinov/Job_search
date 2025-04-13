package com.example.home_work_49.controller;

import com.example.home_work_49.dto.ResumeDto;
import com.example.home_work_49.dto.UserDto;
import com.example.home_work_49.dto.VacancyDto;
import com.example.home_work_49.exceptions.SuchEmailAlreadyExistsException;
import com.example.home_work_49.service.ResumeService;
import com.example.home_work_49.service.UserService;
import com.example.home_work_49.service.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;

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
