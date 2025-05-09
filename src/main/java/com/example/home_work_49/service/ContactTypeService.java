package com.example.home_work_49.service;

import com.example.home_work_49.models.ContactType;

import java.util.Optional;

public interface ContactTypeService {
    Optional<ContactType> findById(Long id);
}
