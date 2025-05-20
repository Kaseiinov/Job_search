package com.example.home_work_49.service;

import com.example.home_work_49.dto.VacancyDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface VacancyService {

    void deleteVacancyById(Long id);

    void updateVacancyById(Long id, VacancyDto vacancyDto);

    Page<VacancyDto> getAllActiveVacancies(int page, int pageSize);

    List<VacancyDto> getVacanciesByUser(String userEmail);

    List<VacancyDto> getVacancyByCategory(String vacancyCategory);

    Page<VacancyDto> getAllActiveVacancyByCreatedDateDesc(int page, int pageSize);

    Page<VacancyDto> getAllActiveVacancyByCreatedDateAsc(int page, int pageSize);

    Page<VacancyDto> getAllActiveVacancyByResponseDesc(int page, int pageSize);

    Page<VacancyDto> getAllActiveVacancyByResponseAsc(int page, int pageSize);

    VacancyDto getVacancyById(Long id);

    void addVacancy(VacancyDto vacancyDto, Authentication auth);
}
