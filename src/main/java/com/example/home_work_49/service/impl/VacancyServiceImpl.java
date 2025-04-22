package com.example.home_work_49.service.impl;

import com.example.home_work_49.dto.VacancyDto;
import com.example.home_work_49.exceptions.*;
import com.example.home_work_49.models.User;
import com.example.home_work_49.models.Vacancy;
import com.example.home_work_49.repository.CategoryRepository;
import com.example.home_work_49.repository.UserRepository;
import com.example.home_work_49.repository.VacancyRepository;
import com.example.home_work_49.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

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
        vacancy.setCategory(categoryRepository.findById(vacancyDto.getCategoryId()).orElseThrow(CategoryNotFountException::new));
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setIsActive(vacancyDto.getIsActive());
        vacancy.setUpdateTime(LocalDateTime.now());

//        boolean exists = vacancyRepository.existsById(id);
//        boolean isCategoryExists = categoryRepository.existsById(vacancyDto.getCategoryId());

//        if (!exists) {
//            throw new VacancyNotFoundException();
//        } else if (!isCategoryExists) {
//            throw new CategoryNotFountException();
//        }
        vacancyRepository.save(vacancy);
    }

    @Override
    public List<VacancyDto> getAllVacancies() {
        List<Vacancy> vacancyList = vacancyRepository.findAll();

        return vacancyBuilder(vacancyList);
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
    public List<VacancyDto> getAllActiveVacancy(){
        List<Vacancy> vacancyList = vacancyRepository.findAllByIsActive(true);
        return vacancyBuilder(vacancyList);
    }

    @Override
    public VacancyDto getVacancyById(Long id){
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(VacancyNotFoundException::new);
        return vacancyBuilder(vacancy);
    }

    @Override
    public void addVacancy(VacancyDto vacancyDto, Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow(UserNotFoundException::new);
        Vacancy vacancy = new Vacancy();
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setCategory(categoryRepository.findById(vacancyDto.getCategoryId()).orElseThrow(CategoryNotFountException::new));
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setIsActive(vacancyDto.getIsActive());
        vacancy.setAuthor(user);
        vacancy.setUpdateTime(null);
        vacancy.setCreatedDate(LocalDateTime.now());

        vacancyRepository.save(vacancy);


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

}
