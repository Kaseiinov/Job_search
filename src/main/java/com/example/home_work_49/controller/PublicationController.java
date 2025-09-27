package com.example.home_work_49.controller;

import com.example.home_work_49.dto.PublicationDto;
import com.example.home_work_49.models.Publication;
import com.example.home_work_49.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("publications")
public class PublicationController {
    private final PublicationService publicationService;

    @GetMapping
    public String publications(@PageableDefault(size = 3)Pageable pageable, Model model) {
        Page<PublicationDto> publications = publicationService.findAll(pageable);
        model.addAttribute("publications", publications.getContent());
        model.addAttribute("items", publications);
        return "publication/publication";
    }
}
