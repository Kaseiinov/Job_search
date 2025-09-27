package com.example.home_work_49.service;

import com.example.home_work_49.dto.PublicationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublicationService {
    Page<PublicationDto> findAll(Pageable pageable);
}
