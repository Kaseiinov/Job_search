package com.example.home_work_49.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDto {
    private Long id;
    @NotBlank
    private Long applicantId;
    @NotBlank
    private String name;
    private Long categoryId;
    private double salary;
    private boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
}
