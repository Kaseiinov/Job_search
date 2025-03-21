package com.example.home_work_49.dto;

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
    private String name;
    private String description;
    private Long categoryId;
    private double salary;
    private Integer expFrom;
    private Integer expTo;
    private boolean isActive;
    private Long authorId;
    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
}
