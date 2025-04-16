package com.example.home_work_49.controller;

import com.example.home_work_49.dto.CategoryDto;
import com.example.home_work_49.dto.ResumeDto;
import com.example.home_work_49.dto.VacancyDto;
import com.example.home_work_49.service.CategoryService;
import com.example.home_work_49.service.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;
    private final CategoryService categoryService;

    @GetMapping
    public String getVacancies(Model model) {
        model.addAttribute("vacancies", vacancyService.getAllVacancies());
        return "employer/vacancies";
    }

    @GetMapping("create")
    public String createVacancy(Model model) {
        VacancyDto vacancyDto = new VacancyDto();
        List<CategoryDto> categories = categoryService.getCategories();
        model.addAttribute("categories",categories );
        model.addAttribute("vacancyDto", vacancyDto);
        return "employer/createVacancy";
    }

    @PostMapping("create")
    public String createVacancy(@Valid VacancyDto vacancyDto, BindingResult bindingResult, Model model, Authentication auth) {
        if(!bindingResult.hasErrors()){
            vacancyService.addVacancy(vacancyDto, auth);
            return "redirect:/vacancies";
        }
        model.addAttribute("vacancyDto", vacancyDto);
        model.addAttribute("categories", categoryService.getCategories());
        return "employer/createVacancy";
    }

    @GetMapping("edit/{vacancyId}")
    public String editVacancy(@PathVariable Long vacancyId, Model model) {
        model.addAttribute("vacancyDto", vacancyService.getVacancyById(vacancyId));
        model.addAttribute("categories", categoryService.getCategories());
        return "employer/edit_vacancy";
    }

    @PostMapping("edit")
    public String editVacancy(@Valid VacancyDto vacancyDto, BindingResult bindingResult, Model model, Authentication auth) {
        if (!bindingResult.hasErrors()) {
            vacancyService.updateVacancyById(vacancyDto.getId(), vacancyDto);
            return "redirect:/users/profile";
        }
        model.addAttribute("vacancyDto", vacancyDto);
        model.addAttribute("categories", categoryService.getCategories());
        return "employer/edit_vacancy";
    }
}
