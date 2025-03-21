package com.example.home_work_49.service.impl;

import com.example.home_work_49.dao.VacancyDao;
import com.example.home_work_49.dto.VacancyDto;
import com.example.home_work_49.models.Vacancy;
import com.example.home_work_49.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class VacancyServiceImpl implements VacancyService {

    private final VacancyDao vacancyDao;

    @Override
    public void addVacancy(VacancyDto vacancyDto) {
        Vacancy vacancy = new Vacancy();
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setCategoryId(vacancyDto.getCategoryId());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setActive(vacancyDto.isActive());
        vacancy.setAuthorId(vacancyDto.getAuthorId());
        vacancy.setCreatedDate(vacancyDto.getCreatedDate());
        vacancy.setUpdateTime(vacancyDto.getUpdateTime());

        vacancyDao.addVacancy(vacancy);
    }

}
