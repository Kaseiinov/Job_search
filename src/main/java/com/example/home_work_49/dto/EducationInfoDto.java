package com.example.home_work_49.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationInfoDto {
    private Long id;
    private Long resumeId;
    private String institution;
    private String program;
    @Past
    private LocalDate startDate;
    @Future
    private LocalDate endDate;
    private String degree;
}
