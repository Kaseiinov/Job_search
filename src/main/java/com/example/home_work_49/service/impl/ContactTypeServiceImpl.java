package com.example.home_work_49.service.impl;

import com.example.home_work_49.models.ContactType;
import com.example.home_work_49.repository.ContactTypeRepository;
import com.example.home_work_49.service.ContactTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactTypeServiceImpl implements ContactTypeService {
    private final ContactTypeRepository contactTypeRepository;

    @Override
    public Optional<ContactType> findById(Long id){
        return contactTypeRepository.findById(id);
    }
}
