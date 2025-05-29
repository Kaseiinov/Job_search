package com.example.home_work_49.service.impl;

import com.example.home_work_49.dto.VacancyDto;
import com.example.home_work_49.exceptions.*;
import com.example.home_work_49.models.User;
import com.example.home_work_49.models.Vacancy;
import com.example.home_work_49.repository.VacancyRepository;
import com.example.home_work_49.service.CategoryService;
import com.example.home_work_49.service.UserService;
import com.example.home_work_49.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;
    private final CategoryService categoryService;
    private final UserService userService;

    @Override
    public Page<VacancyDto> getAllActiveVacancyByContainName(int page, int pageSize, String name) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Vacancy> vacancies = vacancyRepository.findAllVacanciesByNameContainingAndIsActiveIsTrue(name, true, pageable);
        return vacanciesPageBuilder(vacancies, pageable);
    }

    @Override
    public Page<VacancyDto> getAllActiveVacancyByMinSalary(int page, int pageSize, Double salary) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Vacancy> vacancies = vacancyRepository.findAllVacanciesBySalaryGreaterThanEqualAndIsActiveIsTrue(salary, true, pageable);
        return vacanciesPageBuilder(vacancies, pageable);
    }

    @Override
    public void deleteVacancyById(Long id) {
        boolean exists = vacancyRepository.existsById(id);
        if(exists){
            vacancyRepository.deleteById(id);
        }else{
            throw new VacancyNotFoundException();
        }
    }

    @Override
    public void updateVacancyById(Long id, VacancyDto vacancyDto) {

        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(VacancyNotFoundException::new);
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setCategory(categoryService.findByIdModel(vacancyDto.getCategoryId()).orElseThrow(CategoryNotFountException::new));
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setIsActive(vacancyDto.getIsActive());
        vacancy.setUpdateTime(LocalDateTime.now());


        vacancyRepository.save(vacancy);
    }

    @Override
    public Page<VacancyDto> getAllActiveVacancies(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Vacancy> vacancyPage = vacancyRepository.findAllByIsActive(true, pageable);

        return vacanciesPageBuilder(vacancyPage, pageable);
    }

    @Override
    public List<VacancyDto> getVacanciesByUser(String userEmail) {
        List<Vacancy> vacancyList = vacancyRepository.findAllVacanciesByAuthor_Email(userEmail);

        return vacancyBuilder(vacancyList);
    }

    @Override
    public List<VacancyDto> getVacancyByCategory(String vacancyCategory) {
        List<Vacancy> vacancyList = vacancyRepository.findAllByCategory_Name(vacancyCategory);
        return vacancyBuilder(vacancyList);
    }

//    @Override
//    public List<VacancyDto> getVacancyByApplicant(String applicantName) {
//        List<Vacancy> vacancyList =  vacancyDao.getVacancyByApplicant(applicantName);
//
//        return vacancyBuilder(vacancyList);
//    }

    @Override
    public Page<VacancyDto> getAllActiveVacancyByCreatedDateDesc(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Vacancy> vacancyPage = vacancyRepository.findAllByIsActiveOrderByCreatedDateDesc(true, pageable);

        return vacanciesPageBuilder(vacancyPage, pageable);
    }

    @Override
    public Page<VacancyDto> getAllActiveVacancyByCreatedDateAsc(int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Vacancy> vacancyPage = vacancyRepository.findAllByIsActiveOrderByCreatedDateAsc(true, pageable);

        return vacanciesPageBuilder(vacancyPage, pageable);
    }

    @Override
    public Page<VacancyDto> getAllActiveVacancyByResponseDesc(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Vacancy> vacancyPage = vacancyRepository.findAllByIsActiveOrderByResponseDesc(true, pageable);

        return vacanciesPageBuilder(vacancyPage, pageable);
    }

    @Override
    public Page<VacancyDto> getAllActiveVacancyByResponseAsc(int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Vacancy> vacancyPage = vacancyRepository.findAllByIsActiveOrderByResponseASC(true, pageable);

        return vacanciesPageBuilder(vacancyPage, pageable);
    }


    @Override
    public VacancyDto getVacancyById(Long id){
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(VacancyNotFoundException::new);
        return vacancyBuilder(vacancy);
    }

    @Override
    public void addVacancy(VacancyDto vacancyDto, Authentication auth) {
        User user = userService.getUserByEmailModel(auth.getName()).orElseThrow(UserNotFoundException::new);
        Vacancy vacancy = new Vacancy();
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setCategory(categoryService.findByIdModel(vacancyDto.getCategoryId()).orElseThrow(CategoryNotFountException::new));
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setIsActive(vacancyDto.getIsActive());
        vacancy.setAuthor(user);
        vacancy.setUpdateTime(null);
        vacancy.setCreatedDate(LocalDateTime.now());

        vacancyRepository.save(vacancy);


    }

    public List<VacancyDto> vacancyBuilder(List<Vacancy> vacancyList){
        return vacancyList.stream()
                .map(e -> VacancyDto
                        .builder()
                        .id(e.getId())
                        .name(e.getName())
                        .description(e.getDescription())
                        .categoryId(e.getCategory().getId())
                        .salary(e.getSalary())
                        .expFrom(e.getExpFrom())
                        .expTo(e.getExpTo())
                        .isActive(e.getIsActive())
                        .authorId(e.getAuthor().getId())
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
                .categoryId(vacancy.getCategory().getId())
                .salary(vacancy.getSalary())
                .expFrom(vacancy.getExpFrom())
                .expTo(vacancy.getExpTo())
                .isActive(vacancy.getIsActive())
                .authorId(vacancy.getAuthor().getId())
                .createdDate(vacancy.getCreatedDate())
                .updateTime(vacancy.getUpdateTime())
                .build();
    }

    public Page<VacancyDto> vacanciesPageBuilder(Page<Vacancy> vacancies, Pageable pageable) {
        List<VacancyDto> vacancyDtoList = vacancies.stream()
                .map(e -> VacancyDto.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .description(e.getDescription())
                        .categoryId(e.getCategory().getId())
                        .salary(e.getSalary())
                        .expFrom(e.getExpFrom())
                        .expTo(e.getExpTo())
                        .isActive(e.getIsActive())
                        .authorId(e.getAuthor().getId())
                        .createdDate(e.getCreatedDate())
                        .updateTime(e.getUpdateTime())
                        .build())
                .toList();

        return new PageImpl<>(vacancyDtoList, pageable, vacancies.getTotalElements());
    }

}
