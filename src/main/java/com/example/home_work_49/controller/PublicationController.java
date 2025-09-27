package com.example.home_work_49.controller;

import com.example.home_work_49.dto.CategoryDto;
import com.example.home_work_49.dto.CommentDto;
import com.example.home_work_49.dto.PublicationDto;
import com.example.home_work_49.exceptions.IncorrectRoleException;
import com.example.home_work_49.models.Publication;
import com.example.home_work_49.service.CategoryService;
import com.example.home_work_49.service.PublicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("publications")
public class PublicationController {
    private final PublicationService publicationService;
    private final CategoryService categoryService;

    @PostMapping("delete/{id}")
    public String deletePublication(@PathVariable("id") Long id, Authentication authentication) throws IncorrectRoleException {
        publicationService.deletePublication(id, authentication.getName());
        return "redirect:/users/profile/publications";
    }

    @GetMapping("edit/{id}")
    public String showEdit(@PathVariable Long id, Model model) {
        PublicationDto publicationDto = publicationService.findByIdDto(id);
        model.addAttribute("publicationDto", publicationDto);
        model.addAttribute("categories", categoryService.getCategories());
        return "publication/edit";
    }

    @PostMapping("edit/{id}")
    public String updatePublication(@Valid PublicationDto publicationDto, @PathVariable Long id, BindingResult bindingResult, Model model, Authentication auth) throws ChangeSetPersister.NotFoundException {
        if (!bindingResult.hasErrors()) {
            publicationService.update(publicationDto);
            return "redirect:/users/profile/publications";
        }
        model.addAttribute("publicationDto", publicationDto);
        model.addAttribute("categories", categoryService.getCategories());
        return "publication/edit";

    }

    @GetMapping
    public String publications(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String createdDate,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate updatedSince,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 3) Pageable pageable,
            Model model) {

        Page<PublicationDto> publications = publicationService.findWithFilters(
                categoryId, createdDate, sortBy, updatedSince, search, pageable);

        model.addAttribute("publications", publications.getContent());
        model.addAttribute("items", publications);
        model.addAttribute("categories", categoryService.getCategories());

        model.addAttribute("selectedCategoryId", categoryId != null ? categoryId.toString() : "");
        model.addAttribute("selectedCreatedDate", createdDate);
        model.addAttribute("selectedSortBy", sortBy);
        model.addAttribute("selectedUpdatedSince", updatedSince);
        model.addAttribute("selectedSearch", search);

        model.addAttribute("filterParams", generateFilterParams(categoryId, createdDate, sortBy, updatedSince, search));

        return "publication/publication";
    }

    private String generateFilterParams(Long categoryId, String createdDate, String sortBy,
                                        LocalDate updatedSince, String search) {
        StringBuilder params = new StringBuilder();

        if (categoryId != null) {
            params.append("&categoryId=").append(categoryId);
        }
        if (createdDate != null && !createdDate.isEmpty()) {
            params.append("&createdDate=").append(createdDate);
        }
        if (sortBy != null && !sortBy.isEmpty()) {
            params.append("&sortBy=").append(sortBy);
        }
        if (updatedSince != null) {
            params.append("&updatedSince=").append(updatedSince);
        }
        if (search != null && !search.isEmpty()) {
            params.append("&search=").append(URLEncoder.encode(search, StandardCharsets.UTF_8));
        }

        return params.toString();
    }

    @GetMapping("/create")
    public String publicationForm(Model model) {
        List<CategoryDto> categories = categoryService.getCategories();

        model.addAttribute("publicationDto", new PublicationDto());
        model.addAttribute("categories", categories);

        return "publication/createPublication";
    }

    @PostMapping("/create")
    public String createPublication(@Valid PublicationDto publicationDto,  BindingResult bindingResult, Authentication auth, Model model) {
        if (publicationDto.getAvatar() == null || publicationDto.getAvatar().isEmpty()) {
            bindingResult.rejectValue("avatar", "avatar.required", "Avatar is required");
        }

        if(publicationDto.getCategoryId() == null) {
            bindingResult.rejectValue("categoryId", "categoryId.required", "CategoryId is required");
        }

        if (!bindingResult.hasErrors()) {
            publicationDto.setUserEmail(auth.getName());
            publicationService.save(publicationDto);
            return "redirect:/users/profile/publications";
        }

        model.addAttribute("publication", publicationDto);
        model.addAttribute("categories", categoryService.getCategories());
        return "publication/createPublication";
    }
}
