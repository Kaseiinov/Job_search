package com.example.home_work_49.service.impl;

import com.example.home_work_49.dto.PublicationDto;
import com.example.home_work_49.exceptions.CategoryNotFountException;
import com.example.home_work_49.exceptions.UserNotFoundException;
import com.example.home_work_49.models.Publication;
import com.example.home_work_49.repository.PublicationRepository;
import com.example.home_work_49.service.CategoryService;
import com.example.home_work_49.service.PublicationService;
import com.example.home_work_49.service.UserService;
import com.example.home_work_49.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationServiceImpl implements PublicationService {
    private final PublicationRepository publicationRepository;
    private final FileUtil fileUtil;
    private final CategoryService categoryService;
    private final UserService userService;

    @Override
    public void save(PublicationDto publicationDto) {
        Publication publication = new Publication();
        publication.setId(publicationDto.getId());
        publication.setTitle(publicationDto.getTitle());
        publication.setAvatar(fileUtil.saveUploadFile(publicationDto.getAvatar(), "images"));
        publication.setPublicationDate(LocalDate.now());
        publication.setCategory(categoryService.findByIdModel(publicationDto.getCategoryId()).orElseThrow(CategoryNotFountException::new));
        publication.setUser(userService.getUserByEmailModel(publicationDto.getUserEmail()).orElseThrow(UserNotFoundException::new));
        publication.setEnabled(true);
        publication.setDescription(publicationDto.getDescription());

        publicationRepository.save(publication);
    }

    @Override
    public Page<PublicationDto> findByUserEmail(Pageable pageable, String email) {
        Page<Publication> publicationList =  publicationRepository.findAllByUser_Email(email, pageable);
        return convertToDto(publicationList);
    }

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
