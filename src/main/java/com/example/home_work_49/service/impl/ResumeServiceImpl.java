package com.example.home_work_49.service.impl;

import com.example.home_work_49.dao.*;
import com.example.home_work_49.dto.ContactsInfoDto;
import com.example.home_work_49.dto.EducationInfoDto;
import com.example.home_work_49.dto.ResumeDto;
import com.example.home_work_49.dto.WorkExperienceInfoDto;
import com.example.home_work_49.exceptions.CategoryNotFountException;
import com.example.home_work_49.exceptions.ContactNotFoundException;
import com.example.home_work_49.exceptions.ResumeNotFoundException;
import com.example.home_work_49.exceptions.UserNotFoundException;
import com.example.home_work_49.models.*;
import com.example.home_work_49.repository.CategoryRepository;
import com.example.home_work_49.repository.ContactTypeRepository;
import com.example.home_work_49.repository.ResumeRepository;
import com.example.home_work_49.repository.UserRepository;
import com.example.home_work_49.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;
    private final WorkExperienceInfoDao experienceDao;
    private final EducationInfoDao educationDao;
    private final ContactDao contactDao;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ResumeRepository resumeRepository;
    private final ContactTypeRepository contactTypeRepository;

    @Override
    public List<ResumeDto> getAllActiveResumes(){
        List<Resume> resumes = resumeRepository.findAllByIsActive(true);

        return resumeBuilder(resumes);
    }

    @Override
    public void updateResumeById(Long id, ResumeDto resumeDto) {

        Resume resume = new Resume();
        resume.setName(resumeDto.getName());
        resume.setCategory(categoryRepository.findById(resumeDto.getCategoryId()).orElseThrow(CategoryNotFountException::new));
        resume.setSalary(resumeDto.getSalary());
        resume.setIsActive(resumeDto.getIsActive());
        resume.setUpdateTime(LocalDateTime.now());

        boolean exist = resumeDao.resumeIdExists(id);
        boolean isCategoryExists = categoryDao.isCategoryExists(resumeDto.getCategoryId());

        if(!exist ){
            throw new ResumeNotFoundException();
        } else if (!isCategoryExists) {
            throw new CategoryNotFountException();
        }
        resumeDao.updateResumeById(id, resume);


    }

    @Override
    public void deleteResumeById(Long id){
        boolean exist = resumeDao.resumeIdExists(id);
        if(exist){
            resumeDao.deleteResumeById(id);
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

        experienceDao.addWorkExperience(workExp);
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

        educationDao.addEducation(education);
    }

    @Override
    public void addContactInfo(ContactsInfoDto contactDto) {
        ContactInfo contact = new ContactInfo();
        contact.setResume(resumeRepository.findById(contactDto.getResumeId()).orElseThrow(ResumeNotFoundException::new));
        contact.setType(contactTypeRepository.findById(contactDto.getTypeId()).orElseThrow(ContactNotFoundException::new));
        contact.setValue(contactDto.getValue());

        contactDao.addContact(contact);
    }

    @Override
    public void addResume(ResumeDto resumeDto, Authentication auth){
        User user = userDao.getUserByEmail(auth.getName()).orElseThrow(UserNotFoundException::new);

        Resume resume = new Resume();
        resume.setApplicant(userRepository.findById(user.getId()).orElseThrow(UserNotFoundException::new));
        resume.setName(resumeDto.getName());
        resume.setCategory(categoryRepository.findById(resumeDto.getCategoryId()).orElseThrow(CategoryNotFountException::new));
        resume.setSalary(resumeDto.getSalary());
        resume.setIsActive(resumeDto.getIsActive());
        resume.setCreatedDate(LocalDateTime.now());
        resume.setUpdateTime(null);
        resumeDao.addResume(resume);

        Resume savedResume = resumeDao.getResumeByName(resumeDto.getName()).orElseThrow(ResumeNotFoundException::new);

        WorkExperienceInfoDto experienceInfo = resumeDto.getWorkExperience();
        experienceInfo.setResumeId(savedResume.getId());

        addWorkExperienceInfo(experienceInfo);

        EducationInfoDto education = resumeDto.getEducation();
        education.setResumeId(savedResume.getId());

        addEducationInfo(education);

        List<ContactsInfoDto> contactsDto = resumeDto.getContacts();
        contactsDto.forEach(contact -> {
            contact.setResumeId(savedResume.getId());
            addContactInfo(contact);
        });







//        Boolean isApplicant = userDao.isUser(resume.getApplicantId(), "applicant").orElseThrow(UserNotFoundException::new);
//        boolean isCategoryExists = categoryDao.isCategoryExists(resume.getCategoryId());
//
//        if(!isApplicant){
//            throw new ApplicantNotFoundException();
//        } else if (!isCategoryExists) {
//            throw new CategoryNotFountException();
//        }


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
