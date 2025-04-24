package com.example.home_work_49.service.impl;

import com.example.home_work_49.dto.ContactsInfoDto;
import com.example.home_work_49.dto.EducationInfoDto;
import com.example.home_work_49.dto.ResumeDto;
import com.example.home_work_49.dto.WorkExperienceInfoDto;
import com.example.home_work_49.exceptions.CategoryNotFountException;
import com.example.home_work_49.exceptions.ContactNotFoundException;
import com.example.home_work_49.exceptions.ResumeNotFoundException;
import com.example.home_work_49.exceptions.UserNotFoundException;
import com.example.home_work_49.models.*;
import com.example.home_work_49.repository.*;
import com.example.home_work_49.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ResumeRepository resumeRepository;
    private final ContactTypeRepository contactTypeRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final EducationRepository educationRepository;
    private final ContactInfoRepository contactInfoRepository;

    @Override
    public List<ResumeDto> getAllActiveResumes(){
        List<Resume> resumes = resumeRepository.findAllByIsActive(true);

        return resumeBuilder(resumes);
    }

    @Override
    public void updateResumeById(Long id, ResumeDto resumeDto) {

        Resume resume = resumeRepository.findById(id).orElseThrow(ResumeNotFoundException::new);
        resume.setName(resumeDto.getName());
        resume.setCategory(categoryRepository.findById(resumeDto.getCategoryId()).orElseThrow(CategoryNotFountException::new));
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

        workExperienceRepository.save(workExp);
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

        educationRepository.save(education);
    }

    @Override
    public void addContactInfo(ContactsInfoDto contactDto) {
        ContactInfo contact = new ContactInfo();
        contact.setResume(resumeRepository.findById(contactDto.getResumeId()).orElseThrow(ResumeNotFoundException::new));
        contact.setType(contactTypeRepository.findById(contactDto.getTypeId()).orElseThrow(ContactNotFoundException::new));
        contact.setValue(contactDto.getValue());

        contactInfoRepository.save(contact);
    }

    @Override
    public void addResume(ResumeDto resumeDto, Authentication auth){
        User user = userRepository.findByEmail(auth.getName()).orElseThrow(UserNotFoundException::new);

        Resume resume = new Resume();
        resume.setApplicant(user);
        resume.setName(resumeDto.getName());
        resume.setCategory(categoryRepository.findById(resumeDto.getCategoryId()).orElseThrow(CategoryNotFountException::new));
        resume.setSalary(resumeDto.getSalary());
        resume.setIsActive(resumeDto.getIsActive());
        resume.setCreatedDate(LocalDateTime.now());
        resume.setUpdateTime(null);

        resumeRepository.save(resume);

//        Resume savedResume = resumeDao.getResumeByName(resumeDto.getName()).orElseThrow(ResumeNotFoundException::new);

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
        return resumeList
                .stream()
                .map(e -> ResumeDto
                        .builder()
                        .id(e.getId())
                        .applicantId(e.getApplicant().getId())
                        .name(e.getName())
                        .categoryId(e.getCategory().getId())
                        .salary(e.getSalary())
                        .isActive(e.getIsActive())
                        .createdDate(e.getCreatedDate())
                        .updateTime(e.getUpdateTime())
                        .build())
                .toList();
    }

    public ResumeDto resumeBuilder(Resume resume){
        return ResumeDto.builder()
                .id(resume.getId())
                .applicantId(resume.getApplicant().getId())
                .name(resume.getName())
                .categoryId(resume.getCategory().getId())
                .salary(resume.getSalary())
                .isActive(resume.getIsActive())
                .createdDate(resume.getCreatedDate())
                .updateTime(resume.getUpdateTime())
                .build();
    }

}
