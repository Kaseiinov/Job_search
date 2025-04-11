package com.example.home_work_49.controller;

import com.example.home_work_49.dto.ResumeDto;
import com.example.home_work_49.service.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @GetMapping
    public String getResumes(Model model) {
        model.addAttribute("resumes", resumeService.getAllActiveResumes());
        return "applicant/resumes";
    }

    @GetMapping("create")
    public String createResume(Model model) {
        return "applicant/createResume";
    }
    @PostMapping("create")
    public String createResume(@Valid ResumeDto resumeDto) {
        resumeService.addResume(resumeDto);
        return "redirect:/resumes";
    }
}
