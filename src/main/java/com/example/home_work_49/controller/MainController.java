package com.example.home_work_49.controller;

import com.example.home_work_49.dto.VacancyDto;
import com.example.home_work_49.models.Vacancy;
import com.example.home_work_49.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final VacancyService vacancyService;

    @GetMapping
    public String index(@RequestParam(defaultValue = "0")int page,
                        @RequestParam(defaultValue = "default") String sortBy,
                        Model model) {
        int pageSize = 5;

        Page<VacancyDto> vacancies;

        switch (sortBy) {
            case "dateDesc" -> vacancies = vacancyService.getAllActiveVacancyByCreatedDateDesc(page, pageSize);
            case "dateAsc" -> vacancies = vacancyService.getAllActiveVacancyByCreatedDateAsc(page, pageSize);
            case "vacancyResponseDesc" -> vacancies = vacancyService.getAllActiveVacancyByResponseDesc(page, pageSize);
            case "vacancyResponseAsc" -> vacancies = vacancyService.getAllActiveVacancyByResponseAsc(page, pageSize);
            default -> vacancies = vacancyService.getAllActiveVacancies(page, pageSize);
        }

        model.addAttribute("vacancies", vacancies.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("hasNext", vacancies.hasNext());
        model.addAttribute("hasPrevious", vacancies.hasPrevious());
        model.addAttribute("totalPages", vacancies.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        return "index";
    }

}