package com.example.home_work_49.service;

import com.example.home_work_49.dto.VacancyDto;

import java.util.List;

public interface VacancyService {
    List<VacancyDto> getAllVacancies();

    List<VacancyDto> getVacancyByCategory(String vacancyCategory);

    List<VacancyDto> getVacancyByApplicant(String applicantName);

    void addVacancy(VacancyDto vacancyDto);
}
