package com.example.home_work_49.service;

import com.example.home_work_49.dto.ResumeDto;
import com.example.home_work_49.dto.WorkExperienceInfoDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ResumeService {
    List<ResumeDto> getAllActiveResumes();

    void updateResumeById(Long id, ResumeDto resumeDto);

    void deleteResumeById(Long id);

    ResumeDto getResumeById(Long id);

    List<ResumeDto> getResumeByCategory(String resumeCategory);

    List<ResumeDto> getResumeByUser(String userName);

    void addWorkExperienceInfo(WorkExperienceInfoDto workExpDto);


    void addResume(ResumeDto resumeDto, Authentication auth);
}
