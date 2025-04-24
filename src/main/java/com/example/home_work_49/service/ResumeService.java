package com.example.home_work_49.service;

import com.example.home_work_49.dto.ContactsInfoDto;
import com.example.home_work_49.dto.EducationInfoDto;
import com.example.home_work_49.dto.ResumeDto;
import com.example.home_work_49.dto.WorkExperienceInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ResumeService {

    Page<ResumeDto> getAllActiveResumes(int page, int pageSize);

    Page<ResumeDto> getAllActiveResumeByCreatedDateDesc(int page, int pageSize);

    Page<ResumeDto> getAllActiveResumeByCreatedDateAsc(int page, int pageSize);

    void updateResumeById(Long id, ResumeDto resumeDto);

    void deleteResumeById(Long id);

    ResumeDto getResumeById(Long id);

    List<ResumeDto> getResumeByCategory(String resumeCategory);

    List<ResumeDto> getResumeByUser(String userName);

    void addWorkExperienceInfo(WorkExperienceInfoDto workExpDto);


    void addEducationInfo(EducationInfoDto educationDto);

    void addContactInfo(ContactsInfoDto contactDto);

    void addResume(ResumeDto resumeDto, Authentication auth);
}
