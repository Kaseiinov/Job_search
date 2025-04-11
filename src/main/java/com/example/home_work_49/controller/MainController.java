package com.example.home_work_49.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    @GetMapping
    public String index(Model model) {
        model.addAttribute("word", "world");
        return "index";
    }
}