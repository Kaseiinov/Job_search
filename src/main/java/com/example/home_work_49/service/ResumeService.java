package com.example.home_work_49.service;

import com.example.home_work_49.dto.ResumeDto;

import java.util.List;

public interface ResumeService {
    List<ResumeDto> getResumeByCategory(String resumeCategory);

    void addResume(ResumeDto resumeDto);
}
