package com.example.home_work_49.service.impl;

import com.example.home_work_49.models.EducationInfo;
import com.example.home_work_49.repository.EducationRepository;
import com.example.home_work_49.service.EducationInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducationInfoServiceImpl implements EducationInfoService {
    private final EducationRepository educationRepository;

    @Override
    public void save(EducationInfo educationInfo){
        educationRepository.save(educationInfo);
    }
}
