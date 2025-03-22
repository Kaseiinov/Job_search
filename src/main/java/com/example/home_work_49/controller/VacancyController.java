package com.example.home_work_49.controller;

import com.example.home_work_49.dao.ResumeDao;
import com.example.home_work_49.dao.VacancyDao;
import com.example.home_work_49.dto.ResumeDto;
import com.example.home_work_49.dto.VacancyDto;
import com.example.home_work_49.models.Resume;
import com.example.home_work_49.models.Vacancy;
import com.example.home_work_49.service.VacancyService;
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

    @PostMapping
    public HttpStatus createVacancy(@RequestBody VacancyDto vacancyDto) {
        vacancyService.addVacancy(vacancyDto);
        return HttpStatus.CREATED;
    }

    @GetMapping ("vacancies/{applicantName}")
    public List<VacancyDto> getVacancyByApplicant(@PathVariable String applicantName) {
        return vacancyService.getVacancyByApplicant(applicantName);
    }

    @PutMapping("{vacancyId}")
    public ResponseEntity<?> updateVacancy(@PathVariable("vacancyId") Long vacancyId) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping({"vacancyId"})
    public ResponseEntity<?> deleteVacancy(@PathVariable("vacancyId") Long vacancyId) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("category/{vacancyCategory}")
    public List<VacancyDto> getVacancyByCategory(@PathVariable("vacancyCategory") String vacancyCategory) {
        return vacancyService.getVacancyByCategory(vacancyCategory);
    }

//    @GetMapping("aplicants/{aplicantId}")
//    public ResponseEntity<?> getAplicants(@PathVariable long aplicantId) {
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("seeker/{seekerId}")
//    public ResponseEntity<?> getSeekers(@PathVariable long seekerId) {
//        return ResponseEntity.ok().build();
//    }



}
