package com.example.home_work_49.service.impl;

import com.example.home_work_49.dao.CategoryDao;
import com.example.home_work_49.dao.UserDao;
import com.example.home_work_49.dao.VacancyDao;
import com.example.home_work_49.dto.VacancyDto;
import com.example.home_work_49.exceptions.*;
import com.example.home_work_49.models.User;
import com.example.home_work_49.models.Vacancy;
import com.example.home_work_49.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class VacancyServiceImpl implements VacancyService {

    private final VacancyDao vacancyDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;

    @Override
    public void deleteVacancyById(Long id) {
        boolean exists = vacancyDao.vacancyIdExists(id);
        if(exists){
            vacancyDao.deleteVacancyById(id);
        }else{
            throw new VacancyNotFoundException();
        }
    }

    @Override
    public void updateVacancyById(Long id, VacancyDto vacancyDto) {

        Vacancy vacancy = new Vacancy();
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setCategoryId(vacancyDto.getCategoryId());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setIsActive(vacancyDto.getIsActive());
        vacancy.setUpdateTime(LocalDateTime.now());

        boolean exists = vacancyDao.vacancyIdExists(id);
        boolean isCategoryExists = categoryDao.isCategoryExists(vacancyDto.getCategoryId());

        if (!exists) {
            throw new VacancyNotFoundException();
        } else if (!isCategoryExists) {
            throw new CategoryNotFountException();
        }
        vacancyDao.updateVacancyById(id, vacancy);
    }

    @Override
    public List<VacancyDto> getAllVacancies() {
        List<Vacancy> vacancyList = vacancyDao.getAllVacancies();

        return vacancyBuilder(vacancyList);
    }

    @Override
    public List<VacancyDto> getVacanciesByUser(String userEmail) {
        List<Vacancy> vacancyList = vacancyDao.getVacanciesByUser(userEmail);

        return vacancyBuilder(vacancyList);
    }

    @Override
    public List<VacancyDto> getVacancyByCategory(String vacancyCategory) {
        List<Vacancy> vacancyList = vacancyDao.getVacancyByCategory(vacancyCategory);
        return vacancyBuilder(vacancyList);
    }

    @Override
    public List<VacancyDto> getVacancyByApplicant(String applicantName) {
        List<Vacancy> vacancyList =  vacancyDao.getVacancyByApplicant(applicantName);

        return vacancyBuilder(vacancyList);
    }

    @Override
    public List<VacancyDto> getAllActiveVacancy(){
        List<Vacancy> vacancyList = vacancyDao.getAllActiveVacancies();
        return vacancyBuilder(vacancyList);
    }

    @Override
    public VacancyDto getVacancyById(Long id){
        Vacancy vacancy = vacancyDao.getVacancyById(id).orElseThrow(VacancyNotFoundException::new);
        return vacancyBuilder(vacancy);
    }

    @Override
    public void addVacancy(VacancyDto vacancyDto, Authentication auth) {
        User user = userDao.getUserByEmail(auth.getName()).orElseThrow(UserNotFoundException::new);
        Vacancy vacancy = new Vacancy();
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setCategoryId(vacancyDto.getCategoryId());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setIsActive(vacancyDto.getIsActive());
        vacancy.setAuthorId(user.getId());
        vacancy.setUpdateTime(null);
        vacancy.setCreatedDate(LocalDateTime.now());

        vacancyDao.addVacancy(vacancy);


//        Boolean isEmployer = userDao.isUser(vacancyDto.getAuthorId(), "employer").orElseThrow(UserNotFoundException::new);
//        boolean isCategoryExists = categoryDao.isCategoryExists(vacancyDto.getCategoryId());
//
//        if(!isEmployer){
//            throw new EmployerNotFounException();
//        } else if (!isCategoryExists) {
//            throw new CategoryNotFountException();
//        }

    }

    public List<VacancyDto> vacancyBuilder(List<Vacancy> vacancyList){
        return vacancyList.stream()
                .map(e -> VacancyDto
                        .builder()
                        .id(e.getId())
                        .name(e.getName())
                        .description(e.getDescription())
                        .categoryId(e.getCategoryId())
                        .salary(e.getSalary())
                        .expFrom(e.getExpFrom())
                        .expTo(e.getExpTo())
                        .isActive(e.getIsActive())
                        .authorId(e.getAuthorId())
                        .createdDate(e.getCreatedDate())
                        .updateTime(e.getUpdateTime())
                        .build())
                .toList();
    }

    public VacancyDto vacancyBuilder(Vacancy vacancy){
        return VacancyDto
                .builder()
                .id(vacancy.getId())
                .name(vacancy.getName())
                .description(vacancy.getDescription())
                .categoryId(vacancy.getCategoryId())
                .salary(vacancy.getSalary())
                .expFrom(vacancy.getExpFrom())
                .expTo(vacancy.getExpTo())
                .isActive(vacancy.getIsActive())
                .authorId(vacancy.getAuthorId())
                .createdDate(vacancy.getCreatedDate())
                .updateTime(vacancy.getUpdateTime())
                .build();
    }

}
