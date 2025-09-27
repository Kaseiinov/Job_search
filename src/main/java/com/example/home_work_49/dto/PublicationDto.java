package com.example.home_work_49.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationDto {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private MultipartFile avatar;
    private String avatarString;
    private LocalDate publicationDate;
    private LocalDate updateDate;
    private Boolean enabled;
}
