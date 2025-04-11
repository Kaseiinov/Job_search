package com.example.home_work_49.controller;

import com.example.home_work_49.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping
    public String getResumes(Model model) {
        model.addAttribute("vacancies", vacancyService.getAllVacancies());
        return "employer/vacancies";
    }

    @GetMapping("create")
    public String createVacancy(Model model) {
        return "employer/createVacancy";
    }
    @PostMapping("create")
    public String createVacancy() {
        return "redirect:/";
    }
}
