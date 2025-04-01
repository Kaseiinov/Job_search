package com.example.home_work_49.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceInfoDto {
    private Long id;
    @NotNull
    private Long resumeId;
    @NotNull
    private Integer years;
    @NotBlank
    private String companyName;
    @NotBlank
    private String position;
    @NotNull
    private String responsibilities;
}
