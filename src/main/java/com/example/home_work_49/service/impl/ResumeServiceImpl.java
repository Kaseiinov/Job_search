package com.example.home_work_49.service.impl;

import com.example.home_work_49.dao.ResumeDao;
import com.example.home_work_49.dto.ResumeDto;
import com.example.home_work_49.exceptions.ResumeNotFoundException;
import com.example.home_work_49.models.Resume;
import com.example.home_work_49.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;

    @Override
    public List<ResumeDto> getAllActiveResumes(){
        List<Resume> resumes = resumeDao.getAllActiveResumes();

        return resumes
                .stream()
                .map(e -> ResumeDto
                        .builder()
                        .id(e.getId())
                        .applicantId(e.getApplicantId())
                        .name(e.getName())
                        .categoryId(e.getCategoryId())
                        .salary(e.getSalary())
                        .isActive(e.isActive())
                        .createdDate(e.getCreatedDate())
                        .updateTime(e.getUpdateTime())
                        .build())
                .toList();
    }

    @Override
    public void updateResumeById(Long id, ResumeDto resumeDto) {
        boolean exist = resumeDao.resumeIdExists(id);

        Resume resume = new Resume();
        resume.setApplicantId(resumeDto.getApplicantId());
        resume.setName(resumeDto.getName());
        resume.setCategoryId(resumeDto.getCategoryId());
        resume.setSalary(resumeDto.getSalary());
        resume.setActive(resumeDto.isActive());
        resume.setCreatedDate(resumeDto.getCreatedDate());
        resume.setUpdateTime(resumeDto.getUpdateTime());

        if(exist){
            resumeDao.updateResumeById(id, resume);
        }else{
            throw new ResumeNotFoundException();
        }

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
        return ResumeDto.builder()
                .id(resume.getId())
                .applicantId(resume.getApplicantId())
                .name(resume.getName())
                .categoryId(resume.getCategoryId())
                .salary(resume.getSalary())
                .isActive(resume.isActive())
                .createdDate(resume.getCreatedDate())
                .updateTime(resume.getUpdateTime())
                .build();
    }

    @Override
    public List<ResumeDto> getResumeByCategory(String resumeCategory) {
        List<Resume> resumeList = resumeDao.getResumeByCategory(resumeCategory);
        return resumeList
                .stream()
                .map(e -> ResumeDto
                        .builder()
                        .id(e.getId())
                        .applicantId(e.getApplicantId())
                        .name(e.getName())
                        .categoryId(e.getCategoryId())
                        .salary(e.getSalary())
                        .isActive(e.isActive())
                        .createdDate(e.getCreatedDate())
                        .updateTime(e.getUpdateTime())
                        .build())
                .toList();
    }

    @Override
    public List<ResumeDto> getResumeByUser(String userName) {
        List<Resume> resumeList = resumeDao.getResumeByUser(userName);

        return resumeList.stream()
                .map(e -> ResumeDto
                        .builder()
                        .id(e.getId())
                        .applicantId(e.getApplicantId())
                        .name(e.getName())
                        .categoryId(e.getCategoryId())
                        .salary(e.getSalary())
                        .isActive(e.isActive())
                        .createdDate(e.getCreatedDate())
                        .updateTime(e.getUpdateTime())
                        .build())
                .toList();
    }

    @Override
    public void addResume(ResumeDto resumeDto){
        Resume resume = new Resume();
        resume.setApplicantId(resumeDto.getApplicantId());
        resume.setName(resumeDto.getName());
        resume.setCategoryId(resumeDto.getCategoryId());
        resume.setSalary(resumeDto.getSalary());
        resume.setActive(resumeDto.isActive());
        resume.setCreatedDate(resumeDto.getCreatedDate());
        resume.setUpdateTime(resumeDto.getUpdateTime());

        resumeDao.addResume(resume);
    }
}
