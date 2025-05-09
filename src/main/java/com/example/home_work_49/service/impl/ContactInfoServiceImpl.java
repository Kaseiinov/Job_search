package com.example.home_work_49.service.impl;

import com.example.home_work_49.models.ContactInfo;
import com.example.home_work_49.repository.ContactInfoRepository;
import com.example.home_work_49.service.ContactInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactInfoServiceImpl implements ContactInfoService {
    private final ContactInfoRepository contactInfoRepository;

    @Override
    public void save(ContactInfo contactInfo){
        contactInfoRepository.save(contactInfo);
    }
}
