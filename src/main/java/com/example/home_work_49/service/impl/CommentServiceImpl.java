package com.example.home_work_49.service.impl;

import com.example.home_work_49.dto.CommentDto;
import com.example.home_work_49.exceptions.UserNotFoundException;
import com.example.home_work_49.models.Comment;
import com.example.home_work_49.repository.CommentRepository;
import com.example.home_work_49.service.CommentService;
import com.example.home_work_49.service.PublicationService;
import com.example.home_work_49.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PublicationService publicationService;

    @Override
    public void addComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setUser(userService.getUserByEmailModel(commentDto.getUser().getEmail()).orElseThrow(UserNotFoundException::new));
        comment.setPublication(publicationService.findByIdModel(commentDto.getPublicationId()));
        comment.setEnabled(true);

        commentRepository.save(comment);
    }
}
