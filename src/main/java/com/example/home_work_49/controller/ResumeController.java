package com.example.home_work_49.controller;

import com.example.home_work_49.dto.*;
import com.example.home_work_49.service.CategoryService;
import com.example.home_work_49.service.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final CategoryService categoryService;

    @GetMapping
    public String getResumes(Model model) {
        model.addAttribute("resumes", resumeService.getAllActiveResumes());
        return "applicant/resumes";
    }

    @GetMapping("create")
    public String createResume(Model model) {
        List<CategoryDto> categories = categoryService.getCategories();

        ResumeDto resumeDto = new ResumeDto();
        resumeDto.setWorkExperience(new WorkExperienceInfoDto());
        resumeDto.setEducation(new EducationInfoDto());
        resumeDto.setContact(new ContactsInfoDto());
        model.addAttribute("resumeDto", new ResumeDto());
        model.addAttribute("categories", categories);
        return "applicant/createResume";
    }
    @PostMapping("create")
    public String createResume(@Valid ResumeDto resumeDto, BindingResult bindingResult, Model model, Authentication auth) {
        if (!bindingResult.hasErrors()) {
            resumeService.addResume(resumeDto, auth);
            return "redirect:/resumes";
        }
        model.addAttribute("resumeDto", resumeDto);
        model.addAttribute("categories", categoryService.getCategories());
        return "applicant/createResume";
    }
}
