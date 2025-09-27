package com.example.home_work_49.service;

import com.example.home_work_49.dto.PublicationDto;
import com.example.home_work_49.models.Publication;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface PublicationService {
    Page<PublicationDto> findWithFilters(Long categoryId, String createdDate, String sortBy,
                                         LocalDate updatedSince, String search, Pageable pageable);

    void update(PublicationDto publicationDto) throws ChangeSetPersister.NotFoundException;

    void save(PublicationDto publicationDto);

    Publication findByIdModel(Long id);

    PublicationDto findByIdDto(Long id);

    Page<PublicationDto> findByUserEmail(Pageable pageable, String email);

    Page<PublicationDto> findAll(Pageable pageable);
}
