package com.example.home_work_49.dto;

import jakarta.validation.constraints.*;
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
    @NotNull
    private Long categoryId;
    @PositiveOrZero
    private Double salary;
    private Integer expFrom;
    private Integer expTo;
    @NotNull
    private Boolean isActive;
    @NotNull
    private Long authorId;
    @Past
    private LocalDateTime createdDate;
    @Future
    private LocalDateTime updateTime;
}
