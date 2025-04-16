package com.example.home_work_49.service;

import com.example.home_work_49.dto.VacancyDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface VacancyService {
    void deleteVacancyById(Long id);

    void updateVacancyById(Long id, VacancyDto vacancyDto);

    List<VacancyDto> getAllVacancies();

    List<VacancyDto> getVacanciesByUser(String userEmail);

    List<VacancyDto> getVacancyByCategory(String vacancyCategory);

    List<VacancyDto> getVacancyByApplicant(String applicantName);

    void addVacancy(VacancyDto vacancyDto, Authentication auth);
}
