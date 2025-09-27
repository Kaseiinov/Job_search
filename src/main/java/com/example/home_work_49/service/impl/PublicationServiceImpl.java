package com.example.home_work_49.service.impl;

import com.example.home_work_49.dto.PublicationDto;
import com.example.home_work_49.models.Publication;
import com.example.home_work_49.repository.PublicationRepository;
import com.example.home_work_49.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationServiceImpl implements PublicationService {
    private final PublicationRepository publicationRepository;

    @Override
    public Page<PublicationDto> findAll(Pageable pageable) {
        Page<Publication> publicationList =  publicationRepository.findAll(pageable);
        return convertToDto(publicationList);
    }

    public Page<PublicationDto> convertToDto(Page<Publication> publicationList) {
        List<PublicationDto> publicationDtoList = publicationList
                .stream()
                .map(p -> PublicationDto
                        .builder()
                        .id(p.getId())
                        .title(p.getTitle())
                        .description(p.getDescription())
                        .avatarString(p.getAvatar())
                        .publicationDate(p.getPublicationDate())
                        .updateDate(p.getUpdateDate())
                        .enabled(p.getEnabled())
                        .build()
                ).toList();

        return new PageImpl<>(publicationDtoList, publicationList.getPageable(), publicationList.getTotalElements());

    }
}
