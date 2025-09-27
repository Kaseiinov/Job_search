package com.example.home_work_49.controller;

import com.example.home_work_49.dto.CommentDto;
import com.example.home_work_49.models.Comment;
import com.example.home_work_49.service.CommentService;
import com.example.home_work_49.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("comments")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("add")
    public String createComment(@Valid CommentDto comment, BindingResult bindingResult, Authentication auth, Model model) {
        if (!bindingResult.hasErrors()) {
            comment.setUser(userService.getUserByEmail(auth.getName()));
            commentService.addComment(comment);
            return "redirect:/publications";
        }

        model.addAttribute("comment", comment);
        return "publication/publication";
    }
}
