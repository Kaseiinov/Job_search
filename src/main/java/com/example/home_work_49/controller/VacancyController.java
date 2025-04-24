package com.example.home_work_49.controller;

import com.example.home_work_49.dto.CategoryDto;
import com.example.home_work_49.dto.ResumeDto;
import com.example.home_work_49.dto.VacancyDto;
import com.example.home_work_49.service.CategoryService;
import com.example.home_work_49.service.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;
    private final CategoryService categoryService;

    @GetMapping
    public String getVacancies(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "default") String sortBy, Model model) {
        int pageSize = 5;

        Page<VacancyDto> vacancies;

        switch (sortBy) {
            case "dateDesc" -> vacancies = vacancyService.getAllActiveVacancyByCreatedDateDesc(page, pageSize);
            case "dateAsc" -> vacancies = vacancyService.getAllActiveVacancyByCreatedDateAsc(page, pageSize);
            default -> vacancies = vacancyService.getAllActiveVacancies(page, pageSize);
        }

//        Page<VacancyDto> vacancyPage = vacancyService.getAllActiveVacancyByCreatedDateDesc(page, pageSize);
        model.addAttribute("vacancies", vacancies.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("hasNext", vacancies.hasNext());
        model.addAttribute("hasPrevious", vacancies.hasPrevious());
        model.addAttribute("totalPages", vacancies.getTotalPages());
        model.addAttribute("sortBy", sortBy);
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
