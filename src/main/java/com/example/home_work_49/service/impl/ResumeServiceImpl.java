package com.example.home_work_49.service.impl;

import com.example.home_work_49.dao.ResumeDao;
import com.example.home_work_49.dto.ResumeDto;
import com.example.home_work_49.models.Resume;
import com.example.home_work_49.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;

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
