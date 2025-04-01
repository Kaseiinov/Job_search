package com.example.home_work_49.controller;

import com.example.home_work_49.dao.ResumeDao;
import com.example.home_work_49.dao.VacancyDao;
import com.example.home_work_49.dto.ResumeDto;
import com.example.home_work_49.dto.VacancyDto;
import com.example.home_work_49.models.Resume;
import com.example.home_work_49.models.Vacancy;
import com.example.home_work_49.service.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping
    public List<VacancyDto> getAllVacancies() {
        return vacancyService.getAllVacancies();
    }

    @PostMapping("createVacancy")
    public HttpStatus createVacancy(@RequestBody @Valid VacancyDto vacancyDto) {
        vacancyService.addVacancy(vacancyDto);
        return HttpStatus.CREATED;
    }

    @GetMapping ("vacancies/{applicantName}")
    public List<VacancyDto> getVacancyByApplicant(@PathVariable String applicantName) {
        return vacancyService.getVacancyByApplicant(applicantName);
    }

    @PutMapping("update/{vacancyId}")
    public HttpStatus updateVacancy(@PathVariable("vacancyId") @Valid Long vacancyId, @RequestBody VacancyDto vacancyDto) {
        vacancyService.updateVacancyById(vacancyId, vacancyDto);
        return HttpStatus.OK;
    }

    @DeleteMapping("delete/{vacancyId}")
    public HttpStatus deleteVacancyById(@PathVariable("vacancyId") Long vacancyId) {
        vacancyService.deleteVacancyById(vacancyId);
        return HttpStatus.OK;
    }

    @GetMapping("category/{vacancyCategory}")
    public List<VacancyDto> getVacancyByCategory(@PathVariable("vacancyCategory") String vacancyCategory) {
        return vacancyService.getVacancyByCategory(vacancyCategory);
    }


}
