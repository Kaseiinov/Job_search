package com.example.home_work_49.service.impl;

import com.example.home_work_49.models.WorkExperienceInfo;
import com.example.home_work_49.repository.WorkExperienceRepository;
import com.example.home_work_49.service.WorkExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImpl implements WorkExperienceService {
    private final WorkExperienceRepository workExperienceRepository;

    @Override
    public void save(WorkExperienceInfo workExperienceInfo){
        workExperienceRepository.save(workExperienceInfo);
    }
}
