package com.example.home_work_49.service.impl;

import com.example.home_work_49.dto.*;
import com.example.home_work_49.exceptions.CategoryNotFountException;
import com.example.home_work_49.exceptions.ContactNotFoundException;
import com.example.home_work_49.exceptions.ResumeNotFoundException;
import com.example.home_work_49.exceptions.UserNotFoundException;
import com.example.home_work_49.models.*;
import com.example.home_work_49.repository.*;
import com.example.home_work_49.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final UserService userService;
    private final CategoryService categoryService;
    private final ResumeRepository resumeRepository;
    private final ContactTypeService contactTypeService;
    private final WorkExperienceService workExperienceService;
    private final EducationInfoService educationInfoService;
    private final ContactInfoService contactInfoService;

    @Override
    public Page<ResumeDto> getAllActiveResumes(int page, int pageSize){
        Pageable pageable =  PageRequest.of(page,pageSize);
        Page<Resume> resumes = resumeRepository.findAllByIsActive(true, pageable);

        return resumesPageBuilder(resumes, pageable);

    }

    @Override
    public Page<ResumeDto> getAllActiveResumeByCreatedDateDesc(int page, int pageSize){
        Pageable pageable =  PageRequest.of(page,pageSize);
        Page<Resume> resumes = resumeRepository.findAllByIsActiveOrderByCreatedDateDesc(true, pageable);
        return resumesPageBuilder(resumes, pageable);
    }

    @Override
    public Page<ResumeDto> getAllActiveResumeByCreatedDateAsc(int page, int pageSize){
        Pageable pageable =  PageRequest.of(page,pageSize);
        Page<Resume> resumes = resumeRepository.findAllByIsActiveOrderByCreatedDateAsc(true, pageable);
        return resumesPageBuilder(resumes, pageable);
    }

    @Override
    public void updateResumeById(Long id, ResumeDto resumeDto) {

        Resume resume = resumeRepository.findById(id).orElseThrow(ResumeNotFoundException::new);
        resume.setName(resumeDto.getName());
        resume.setCategory(categoryService.findByIdModel(resumeDto.getCategoryId()).orElseThrow(CategoryNotFountException::new));
        resume.setSalary(resumeDto.getSalary());
        resume.setIsActive(resumeDto.getIsActive());
        resume.setUpdateTime(LocalDateTime.now());

//        boolean exist = resumeRepository.existsById(id);
//        boolean isCategoryExists = categoryRepository.existsById(resumeDto.getCategoryId());

//        if(!exist ){
//            throw new ResumeNotFoundException();
//        } else if (!isCategoryExists) {
//            throw new CategoryNotFountException();
//        }
        resumeRepository.save(resume);


    }

    @Override
    public void deleteResumeById(Long id){
        boolean exist = resumeRepository.existsById(id);
        if(exist){
            resumeRepository.deleteById(id);
        }else{
            throw new ResumeNotFoundException();

        }
    }

    @Override
    public ResumeDto getResumeById(Long id){
        Resume resume = resumeRepository.findById(id).orElseThrow(ResumeNotFoundException::new);
        return resumeBuilder(resume);
    }

    @Override
    public List<ResumeDto> getResumeByCategory(String resumeCategory) {
        List<Resume> resumeList = resumeRepository.findAllByCategory_Name(resumeCategory);
        return resumeBuilder(resumeList);
    }

    @Override
    public List<ResumeDto> getResumeByUser(String userEmail) {
        List<Resume> resumeList = resumeRepository.findAllByApplicant_Email(userEmail);

        return resumeBuilder(resumeList);
    }

    @Override
    public void addWorkExperienceInfo(WorkExperienceInfoDto workExpDto) {
        WorkExperienceInfo workExp = new WorkExperienceInfo();
        workExp.setResume(resumeRepository.findById(workExpDto.getResumeId()).orElseThrow(ResumeNotFoundException::new));
        workExp.setYears(workExpDto.getYears());
        workExp.setCompanyName(workExpDto.getCompanyName());
        workExp.setPosition(workExpDto.getPosition());
        workExp.setResponsibilities(workExpDto.getResponsibilities());

        workExperienceService.save(workExp);
    }

    @Override
    public void addEducationInfo(EducationInfoDto educationDto) {
        EducationInfo education = new EducationInfo();
        education.setResume(resumeRepository.findById(educationDto.getResumeId()).orElseThrow(ResumeNotFoundException::new));
        education.setInstitution(educationDto.getInstitution());
        education.setProgram(educationDto.getProgram());
        education.setStartDate(educationDto.getStartDate());
        education.setEndDate(educationDto.getEndDate());
        education.setDegree(educationDto.getDegree());

        educationInfoService.save(education);
    }

    @Override
    public void addContactInfo(ContactsInfoDto contactDto) {
        ContactInfo contact = new ContactInfo();
        contact.setResume(resumeRepository.findById(contactDto.getResumeId()).orElseThrow(ResumeNotFoundException::new));
        contact.setType(contactTypeService.findById(contactDto.getTypeId()).orElseThrow(ContactNotFoundException::new));
        contact.setValue(contactDto.getValue());

        contactInfoService.save(contact);
    }

    @Override
    public void addResume(ResumeDto resumeDto, Authentication auth){
        User user = userService.getUserByEmailModel(auth.getName()).orElseThrow(UserNotFoundException::new);

        Resume resume = new Resume();
        resume.setApplicant(user);
        resume.setName(resumeDto.getName());
        resume.setCategory(categoryService.findByIdModel(resumeDto.getCategoryId()).orElseThrow(CategoryNotFountException::new));
        resume.setSalary(resumeDto.getSalary());
        resume.setIsActive(resumeDto.getIsActive());
        resume.setCreatedDate(LocalDateTime.now());
        resume.setUpdateTime(null);

        resumeRepository.save(resume);


        if (resumeDto.getWorkExperience() != null) {
            WorkExperienceInfoDto experience = resumeDto.getWorkExperience();
            experience.setResumeId(resume.getId());
            addWorkExperienceInfo(experience);
        }

        if (resumeDto.getEducation() != null) {
            EducationInfoDto education = resumeDto.getEducation();
            education.setResumeId(resume.getId());
            addEducationInfo(education);
        }

        if (resumeDto.getContacts() != null) {
            List<ContactsInfoDto> contacts = resumeDto.getContacts().stream()
                    .filter(contact -> contact.getValue() != null && !contact.getValue().isBlank())
                    .toList();

            contacts.forEach(contact -> {
                contact.setResumeId(resume.getId());
                addContactInfo(contact);
            });
        }
    }

    public List<ResumeDto> resumeBuilder(List<Resume> resumeList){
        return resumeList.stream()
                .map(e -> {
                    WorkExperienceInfoDto experienceDto = null;
                    if (e.getExperience() != null) {
                        experienceDto = WorkExperienceInfoDto.builder()
                                .id(e.getExperience().getId())
                                .companyName(e.getExperience().getCompanyName())
                                .position(e.getExperience().getPosition())
                                .responsibilities(e.getExperience().getResponsibilities())
                                .resumeId(e.getId())
                                .years(e.getExperience().getYears())
                                .build();
                    }

                    EducationInfoDto educationDto = null;
                    if (e.getEducation() != null) {
                        educationDto = EducationInfoDto.builder()
                                .id(e.getEducation().getId())
                                .resumeId(e.getId())
                                .degree(e.getEducation().getDegree())
                                .institution(e.getEducation().getInstitution())
                                .program(e.getEducation().getProgram())
                                .startDate(e.getEducation().getStartDate())
                                .endDate(e.getEducation().getEndDate())
                                .build();
                    }

                    List<ContactsInfoDto> contactsDto = e.getContacts() != null
                            ? e.getContacts().stream()
                            .map(ed -> ContactsInfoDto.builder()
                                    .id(ed.getId())
                                    .resumeId(e.getId())
                                    .typeId(ed.getType().getId())
                                    .value(ed.getValue())
                                    .build())
                            .toList()
                            : List.of();

                    return ResumeDto.builder()
                            .id(e.getId())
                            .user(UserDto.builder()
                                    .id(e.getApplicant().getId())
                                    .name(e.getApplicant().getName())
                                    .surname(e.getApplicant().getSurname())
                                    .email(e.getApplicant().getEmail())
                                    .age(e.getApplicant().getAge())
                                    .phoneNumber(e.getApplicant().getPhoneNumber())
                                    .build())
                            .name(e.getName())
                            .categoryId(e.getCategory().getId())
                            .salary(e.getSalary())
                            .isActive(e.getIsActive())
                            .createdDate(e.getCreatedDate())
                            .updateTime(e.getUpdateTime())
                            .workExperience(experienceDto)
                            .education(educationDto)
                            .contacts(contactsDto)
                            .build();
                })
                .toList();
    }

    public ResumeDto resumeBuilder(Resume resume) {
        WorkExperienceInfoDto experienceDto = null;
        if (resume.getExperience() != null) {
            experienceDto = WorkExperienceInfoDto.builder()
                    .id(resume.getExperience().getId())
                    .companyName(resume.getExperience().getCompanyName())
                    .position(resume.getExperience().getPosition())
                    .responsibilities(resume.getExperience().getResponsibilities())
                    .resumeId(resume.getId())
                    .years(resume.getExperience().getYears())
                    .build();
        }

        EducationInfoDto educationDto = null;
        if (resume.getEducation() != null) {
            educationDto = EducationInfoDto.builder()
                    .id(resume.getEducation().getId())
                    .resumeId(resume.getId())
                    .degree(resume.getEducation().getDegree())
                    .institution(resume.getEducation().getInstitution())
                    .program(resume.getEducation().getProgram())
                    .startDate(resume.getEducation().getStartDate())
                    .endDate(resume.getEducation().getEndDate())
                    .build();
        }

        List<ContactsInfoDto> contactsDto = resume.getContacts() != null
                ? resume.getContacts().stream()
                .map(ed -> ContactsInfoDto.builder()
                        .id(ed.getId())
                        .resumeId(resume.getId())
                        .typeId(ed.getType().getId())
                        .value(ed.getValue())
                        .build())
                .toList()
                : List.of();

        return ResumeDto.builder()
                .id(resume.getId())
                .user(UserDto.builder()
                        .id(resume.getApplicant().getId())
                        .name(resume.getApplicant().getName())
                        .surname(resume.getApplicant().getSurname())
                        .email(resume.getApplicant().getEmail())
                        .age(resume.getApplicant().getAge())
                        .phoneNumber(resume.getApplicant().getPhoneNumber())
                        .build())
                .name(resume.getName())
                .categoryId(resume.getCategory().getId())
                .salary(resume.getSalary())
                .isActive(resume.getIsActive())
                .createdDate(resume.getCreatedDate())
                .updateTime(resume.getUpdateTime())
                .workExperience(experienceDto)
                .education(educationDto)
                .contacts(contactsDto)
                .build();
    }


    public Page<ResumeDto> resumesPageBuilder(Page<Resume> resumes, Pageable pageable) {
        List<ResumeDto> resumeDtoList = resumes.stream()
                .map(e -> {
                    WorkExperienceInfoDto experienceDto = null;
                    if (e.getExperience() != null) {
                        experienceDto = WorkExperienceInfoDto.builder()
                                .id(e.getExperience().getId())
                                .companyName(e.getExperience().getCompanyName())
                                .position(e.getExperience().getPosition())
                                .responsibilities(e.getExperience().getResponsibilities())
                                .resumeId(e.getId())
                                .years(e.getExperience().getYears())
                                .build();
                    }

                    EducationInfoDto educationDto = null;
                    if (e.getEducation() != null) {
                        educationDto = EducationInfoDto.builder()
                                .id(e.getEducation().getId())
                                .resumeId(e.getId())
                                .degree(e.getEducation().getDegree())
                                .institution(e.getEducation().getInstitution())
                                .program(e.getEducation().getProgram())
                                .startDate(e.getEducation().getStartDate())
                                .endDate(e.getEducation().getEndDate())
                                .build();
                    }

                    List<ContactsInfoDto> contactsDto = e.getContacts() != null
                            ? e.getContacts().stream()
                            .map(ed -> ContactsInfoDto.builder()
                                    .id(ed.getId())
                                    .resumeId(e.getId())
                                    .typeId(ed.getType().getId())
                                    .value(ed.getValue())
                                    .build())
                            .toList()
                            : List.of();

                    UserImageDto userImageDto = new UserImageDto();
                    if(e.getApplicant().getAvatar() != null){
                        userImageDto.setUserId(e.getApplicant().getId());
                        userImageDto.setId(e.getApplicant().getAvatar().getId());
                        userImageDto.setFileName(e.getApplicant().getAvatar().getFileName());
                    }


                    return ResumeDto.builder()
                            .id(e.getId())
                            .user(UserDto.builder()
                                    .id(e.getApplicant().getId())
                                    .name(e.getApplicant().getName())
                                    .surname(e.getApplicant().getSurname())
                                    .email(e.getApplicant().getEmail())
                                    .age(e.getApplicant().getAge())
                                    .phoneNumber(e.getApplicant().getPhoneNumber())
                                    .avatar(userImageDto)
                                    .build())
                            .name(e.getName())
                            .categoryId(e.getCategory().getId())
                            .salary(e.getSalary())
                            .isActive(e.getIsActive())
                            .createdDate(e.getCreatedDate())
                            .updateTime(e.getUpdateTime())
                            .workExperience(experienceDto)
                            .education(educationDto)
                            .contacts(contactsDto)
                            .build();
                })
                .toList();

        return new PageImpl<>(resumeDtoList, pageable, resumes.getTotalElements());
    }


}
