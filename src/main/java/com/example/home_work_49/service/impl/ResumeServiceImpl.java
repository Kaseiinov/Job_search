package com.example.home_work_49.service.impl;

import com.example.home_work_49.dao.CategoryDao;
import com.example.home_work_49.dao.ResumeDao;
import com.example.home_work_49.dao.UserDao;
import com.example.home_work_49.dto.ResumeDto;
import com.example.home_work_49.dto.WorkExperienceInfoDto;
import com.example.home_work_49.exceptions.ApplicantNotFoundException;
import com.example.home_work_49.exceptions.CategoryNotFountException;
import com.example.home_work_49.exceptions.ResumeNotFoundException;
import com.example.home_work_49.exceptions.UserNotFoundException;
import com.example.home_work_49.models.Resume;
import com.example.home_work_49.models.User;
import com.example.home_work_49.models.WorkExperienceInfo;
import com.example.home_work_49.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;

    @Override
    public List<ResumeDto> getAllActiveResumes(){
        List<Resume> resumes = resumeDao.getAllActiveResumes();

        return resumeBuilder(resumes);
    }

    @Override
    public void updateResumeById(Long id, ResumeDto resumeDto) {

        Resume resume = new Resume();
        resume.setName(resumeDto.getName());
        resume.setCategoryId(resumeDto.getCategoryId());
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
        Resume resume = resumeDao.getResumeById(id).orElseThrow(ResumeNotFoundException::new);
        return resumeBuilder(resume);
    }

    @Override
    public List<ResumeDto> getResumeByCategory(String resumeCategory) {
        List<Resume> resumeList = resumeDao.getResumeByCategory(resumeCategory);
        return resumeBuilder(resumeList);
    }

    @Override
    public List<ResumeDto> getResumeByUser(String userName) {
        List<Resume> resumeList = resumeDao.getResumeByUser(userName);

        return resumeBuilder(resumeList);
    }

    @Override
    public void addWorkExperienceInfo(WorkExperienceInfoDto workExpDto) {
        WorkExperienceInfo workExp = new WorkExperienceInfo();
        workExp.setResumeId(workExpDto.getResumeId());
        workExp.setYears(workExpDto.getYears());
        workExp.setCompanyName(workExpDto.getCompanyName());
        workExp.setPosition(workExpDto.getPosition());
        workExp.setResponsibilities(workExpDto.getResponsibilities());

        resumeDao.addWorkExperienceInfo(workExp);
    }

    @Override
    public void addResume(ResumeDto resumeDto){

        Resume resume = new Resume();
        resume.setApplicantId(resumeDto.getApplicantId());
        resume.setName(resumeDto.getName());
        resume.setCategoryId(resumeDto.getCategoryId());
        resume.setSalary(resumeDto.getSalary());
        resume.setIsActive(resumeDto.getIsActive());
        resume.setCreatedDate(LocalDateTime.now());
        resume.setUpdateTime(null);

        Boolean isApplicant = userDao.isUser(resumeDto.getApplicantId(), "applicant").orElseThrow(UserNotFoundException::new);
        boolean isCategoryExists = categoryDao.isCategoryExists(resumeDto.getCategoryId());

        if(!isApplicant){
            throw new ApplicantNotFoundException();
        } else if (!isCategoryExists) {
            throw new CategoryNotFountException();
        }
        resumeDao.addResume(resume);


    }

    public List<ResumeDto> resumeBuilder(List<Resume> resumeList){
        return resumeList
                .stream()
                .map(e -> ResumeDto
                        .builder()
                        .id(e.getId())
                        .applicantId(e.getApplicantId())
                        .name(e.getName())
                        .categoryId(e.getCategoryId())
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
                .applicantId(resume.getApplicantId())
                .name(resume.getName())
                .categoryId(resume.getCategoryId())
                .salary(resume.getSalary())
                .isActive(resume.getIsActive())
                .createdDate(resume.getCreatedDate())
                .updateTime(resume.getUpdateTime())
                .build();
    }

}
