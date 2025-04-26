package com.example.home_work_49.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDto {
    private Long id;
    private UserDto user;
    @NotBlank
    private String name;
    @NotNull
    private Long categoryId;
    @NotNull
    @PositiveOrZero
    private Double salary;
    @NotNull
    private Boolean isActive;
    @Past
    private LocalDateTime createdDate;
    @Future
    private LocalDateTime updateTime;
    private WorkExperienceInfoDto workExperience;
    private EducationInfoDto education;
    private List<ContactsInfoDto> contacts;


    public String getCreatedDateFormatted() {
        return createdDate != null
                ? createdDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
                : "";
    }

    public String getUpdateTimeFormatted() {
        return updateTime != null
                ? updateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
                : "";
    }
}
