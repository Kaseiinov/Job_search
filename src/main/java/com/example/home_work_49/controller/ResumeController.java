package com.example.home_work_49.controller;

import com.example.home_work_49.dto.*;
import com.example.home_work_49.service.CategoryService;
import com.example.home_work_49.service.ResumeService;
import com.example.home_work_49.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping
    public String getResumes(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "default") String sortBy, Model model) {
        int pageSize = 5;

        Page<ResumeDto> resumes;

        switch (sortBy) {
            case "dateDesc" -> resumes = resumeService.getAllActiveResumeByCreatedDateDesc(page, pageSize);
            case "dateAsc" -> resumes = resumeService.getAllActiveResumeByCreatedDateAsc(page, pageSize);
            default -> resumes = resumeService.getAllActiveResumes(page, pageSize);
        }

        model.addAttribute("resumes", resumes.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("hasNext", resumes.hasNext());
        model.addAttribute("hasPrevious", resumes.hasPrevious());
        model.addAttribute("totalPages", resumes.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        return "applicant/resumes";
    }

    @GetMapping("create")
    public String createResume(Model model) {
        List<CategoryDto> categories = categoryService.getCategories();

        ResumeDto resumeDto = new ResumeDto();
        resumeDto.setWorkExperience(new WorkExperienceInfoDto());
        resumeDto.setEducation(new EducationInfoDto());
        resumeDto.setContacts(new ArrayList<>());
        model.addAttribute("resumeDto", new ResumeDto());
        model.addAttribute("categories", categories);
        return "applicant/createResume";
    }
    @PostMapping("create")
    public String createResume(@Valid ResumeDto resumeDto, BindingResult bindingResult, Model model, Authentication auth) {
        if (!bindingResult.hasErrors()) {
            resumeService.addResume(resumeDto, auth);
            System.out.println("Редирект на /resumes");
            return "redirect:/users/profile";
        }
        model.addAttribute("resumeDto", resumeDto);
        model.addAttribute("categories", categoryService.getCategories());
        return "applicant/createResume";
    }

    @GetMapping("edit/{resumeId}")
    public String editResume(@PathVariable Long resumeId, Model model) {
        model.addAttribute("resumeDto", resumeService.getResumeById(resumeId));
        model.addAttribute("categories", categoryService.getCategories());
        return "applicant/edit_resume";
    }

    @PostMapping("edit")
    public String editResume(@Valid ResumeDto resumeDto, BindingResult bindingResult, Model model, Authentication auth) {
        if (!bindingResult.hasErrors()) {
            resumeService.updateResumeById(resumeDto.getId(), resumeDto);
            return "redirect:/users/profile";
        }
        model.addAttribute("resumeDto", resumeDto);
        model.addAttribute("categories", categoryService.getCategories());
        return "applicant/edit_resume";
    }
}
