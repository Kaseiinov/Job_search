package com.example.home_work_49.controller;

import com.example.home_work_49.dto.*;
import com.example.home_work_49.exceptions.SuchEmailAlreadyExistsException;
import com.example.home_work_49.models.User;
import com.example.home_work_49.repository.UserImageRepository;
import com.example.home_work_49.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private final PublicationService publicationService;

    @GetMapping("profile")
    public String getProfile(@PageableDefault(size = 5)Pageable pageable, Model model , Authentication auth){
        String username = auth.getName();
        Page<ResumeDto> resumes = resumeService.getPageResumeByUser(pageable, username);
        Page<VacancyDto> vacancies = vacancyService.getPageVacanciesByUser(username, pageable);
//        Page<PublicationDto> publications = publicationService.findByUserEmail(pageable, auth.getName());
        UserDto userDto = userService.getUserByEmail(username);
        if(userDto.getAccountType().equalsIgnoreCase("employer")){
            model.addAttribute("items", vacancies.getContent());
            model.addAttribute("vacancyItems", vacancies);
        }else if(userDto.getAccountType().equalsIgnoreCase("applicant")){
            model.addAttribute("items", resumes.getContent());
            model.addAttribute("resumeItems", resumes);

        }
//        model.addAttribute("publications", publications.getContent());
//        model.addAttribute("publicItems", publications);
        model.addAttribute("user",userDto);
        return "auth/profile";
    }

    @GetMapping("edit/{userEmail}")
    public String updateUser(Model model, @PathVariable String userEmail){
        UserEditDto user = userService.getUserByEmailByEditType(userEmail);
        model.addAttribute("userEditDto", user);
        return "auth/edit";
    }

    @PostMapping("edit")
    public String updateUser(@Valid UserEditDto user, BindingResult bindingResult, Model model) throws SuchEmailAlreadyExistsException, RoleNotFoundException {
        if(!bindingResult.hasErrors()){
            userService.updateUserByEmail(user.getEmail(), user);
            return "redirect:/users/profile";
        }
        model.addAttribute("userEditDto", user);
        return "auth/edit";
    }

}
