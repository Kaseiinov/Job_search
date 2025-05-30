package com.example.home_work_49.controller.api;

import com.example.home_work_49.dto.VacancyDto;
import com.example.home_work_49.service.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("restVacancy")
@RequestMapping("api/vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping
    public ResponseEntity<List<VacancyDto>> getFilteredVacancies(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minPrice) {

        List<VacancyDto> vacancies = vacancyService.getFilteredVacancies(
                categoryId,
                minPrice
        );

        System.out.println(vacancies);

        return ResponseEntity.ok(vacancies);
    }

//    @GetMapping
//    public List<VacancyDto> getAllVacancies() {
//        return vacancyService.getAllVacancies();
//    }

    @PostMapping("createVacancy")
    public HttpStatus createVacancy(@RequestBody @Valid VacancyDto vacancyDto) {
        log.info("Creating vacancy: {}", vacancyDto.getName());
//        vacancyService.addVacancy(vacancyDto);
        return HttpStatus.CREATED;
    }

//    @GetMapping ("vacancies/{applicantName}")
//    public List<VacancyDto> getVacancyByApplicant(@PathVariable String applicantName) {
//        return vacancyService.getVacancyByApplicant(applicantName);
//    }

    @PutMapping("update/{vacancyId}")
    public HttpStatus updateVacancy(@PathVariable("vacancyId") @Valid Long vacancyId, @RequestBody VacancyDto vacancyDto) {
        vacancyService.updateVacancyById(vacancyId, vacancyDto);
        return HttpStatus.OK;
    }

    @DeleteMapping("delete/{vacancyId}")
    public HttpStatus deleteVacancyById(@PathVariable("vacancyId") Long vacancyId) {
        log.warn("Deleting vacancy: {}", vacancyId);
        vacancyService.deleteVacancyById(vacancyId);
        return HttpStatus.OK;
    }

    @GetMapping("category/{vacancyCategory}")
    public List<VacancyDto> getVacancyByCategory(@PathVariable("vacancyCategory") String vacancyCategory) {
        return vacancyService.getVacancyByCategory(vacancyCategory);
    }


}
