package com.example.home_work_49.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyDto {
    private Long id;
    @NotBlank
    private String name;
    private String description;
    private Long categoryId;
    private double salary;
    private Integer expFrom;
    private Integer expTo;
    private boolean isActive;
    @NotNull
    private Long authorId;
    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
}
