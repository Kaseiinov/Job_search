package com.example.home_work_49.controller;

import com.example.home_work_49.dto.CategoryDto;
import com.example.home_work_49.dto.PublicationDto;
import com.example.home_work_49.models.Publication;
import com.example.home_work_49.service.CategoryService;
import com.example.home_work_49.service.PublicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("publications")
public class PublicationController {
    private final PublicationService publicationService;
    private final CategoryService categoryService;

    @GetMapping
    public String publications(@PageableDefault(size = 3)Pageable pageable, Model model) {
        Page<PublicationDto> publications = publicationService.findAll(pageable);
        model.addAttribute("publications", publications.getContent());
        model.addAttribute("items", publications);
        return "publication/publication";
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
