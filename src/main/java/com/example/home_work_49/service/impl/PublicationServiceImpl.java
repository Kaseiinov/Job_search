package com.example.home_work_49.service.impl;

import com.example.home_work_49.dto.CommentDto;
import com.example.home_work_49.dto.PublicationDto;
import com.example.home_work_49.exceptions.CategoryNotFountException;
import com.example.home_work_49.exceptions.IncorrectRoleException;
import com.example.home_work_49.exceptions.PublicationNotFoundException;
import com.example.home_work_49.exceptions.UserNotFoundException;
import com.example.home_work_49.models.Publication;
import com.example.home_work_49.models.User;
import com.example.home_work_49.repository.PublicationRepository;
import com.example.home_work_49.service.CategoryService;
import com.example.home_work_49.service.PublicationService;
import com.example.home_work_49.service.UserService;
import com.example.home_work_49.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
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

    private boolean isAdmin(User user) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getRole().equals("ROLE_ADMIN") || role.getRole().equals("ADMIN"));
    }

    @Override
    public void deletePublication(Long id, String email) throws IncorrectRoleException {
        Publication publication = publicationRepository.findById(id).orElseThrow(PublicationNotFoundException::new);
        User user = userService.getUserByEmailModel(email).orElseThrow(UserNotFoundException::new);
        if(publication.getUser().equals(user) || isAdmin(user)) {
            publication.setEnabled(false);
            publicationRepository.save(publication);
        }else {
            throw new IncorrectRoleException();
        }
    }

    @Override
    public Page<PublicationDto> findWithFilters(Long categoryId, String createdDate, String sortBy,
                                                LocalDate updatedSince, String search, Pageable pageable) {

        Specification<Publication> spec = Specification.where(null);

        if (categoryId != null) {
            spec = spec.and(PublicationSpecifications.hasCategory(categoryId));
        }

        if (createdDate != null) {
            LocalDate dateFilter = switch (createdDate) {
                case "today" -> LocalDate.now();
                case "week" -> LocalDate.now().minusWeeks(1);
                case "month" -> LocalDate.now().minusMonths(1);
                case "year" -> LocalDate.now().minusYears(1);
                default -> null;
            };
            if (dateFilter != null) {
                spec = spec.and(PublicationSpecifications.createdAfter(dateFilter));
            }
        }

        if (updatedSince != null) {
            spec = spec.and(PublicationSpecifications.updatedAfter(updatedSince));
        }

        if (search != null && !search.trim().isEmpty()) {
            spec = spec.and(PublicationSpecifications.containsText(search));
        }

        Pageable sortedPageable = getSortedPageable(sortBy, pageable);

        Page<Publication> publications = publicationRepository.findEnabledPublications(spec, sortedPageable);
        return convertToDto(publications);
    }

    private Pageable getSortedPageable(String sortBy, Pageable pageable) {
        if (sortBy == null) {
            return pageable;
        }

        Sort sort = switch (sortBy) {
            case "oldest" -> Sort.by("publicationDate").ascending();
            case "updated" -> Sort.by("updateDate").descending();
            case "mostCommented" -> Sort.by("comments.size").descending();
            default -> Sort.by("publicationDate").descending();
        };

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }

    @Override
    public void update(PublicationDto publicationDto) throws ChangeSetPersister.NotFoundException {
        Publication publication = publicationRepository.findById(publicationDto.getId()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        publication.setTitle(publicationDto.getTitle());
        publication.setDescription(publicationDto.getDescription());
        publication.setAvatar(fileUtil.saveUploadFile(publicationDto.getAvatar(), "images"));
        publication.setUpdateDate(LocalDate.now());
        publicationRepository.save(publication);
    }


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
    public Publication findByIdModel(Long id){
        return publicationRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public PublicationDto findByIdDto(Long id){
        return convertToDto(publicationRepository.findById(id).orElseThrow(UserNotFoundException::new)) ;
    }


    @Override
    public Page<PublicationDto> findByUserEmail(Pageable pageable, String email) {
        Page<Publication> publicationList =  publicationRepository.findAllByEnabledAndUser_Email(true, email, pageable);
        return convertToDto(publicationList);
    }

    @Override
    public Page<PublicationDto> findAll(Pageable pageable) {
        Page<Publication> publicationList =  publicationRepository.findAll(pageable);
        return convertToDto(publicationList);
    }

    public PublicationDto convertToDto(Publication publication){
        return PublicationDto
                .builder()
                .id(publication.getId())
                .title(publication.getTitle())
                .description(publication.getDescription())
                .publicationDate(publication.getPublicationDate())
                .updateDate(publication.getUpdateDate())
                .build();
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
                        .comments(p.getComments()
                                .stream()
                                .map(c -> CommentDto
                                        .builder()
                                        .id(c.getId())
                                        .content(c.getContent())
                                        .avatar(c.getAvatar())
                                        .createdDate(c.getCreatedDate())
                                        .enabled(c.getEnabled())
                                        .user(userService.getUserByEmail(p.getUser().getEmail()))
                                        .build()
                                ).toList()
                        )
                        .build()
                ).toList();

        return new PageImpl<>(publicationDtoList, publicationList.getPageable(), publicationList.getTotalElements());

    }
}
