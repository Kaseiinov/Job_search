package com.example.home_work_49.dto;

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
    private String period;
    private String degree;
}
