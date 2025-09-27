package com.example.home_work_49.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePublication {
    private Long id;
    private String title;
    private String description;
    private MultipartFile avatar;
    private String avatarString;
    private LocalDate publicationDate;
    private LocalDate updateDate;
    private Boolean enabled;
    private Long categoryId;
    private String userEmail;
    private List<CommentDto> comments;
}
