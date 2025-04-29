package com.example.home_work_49.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @PositiveOrZero
    private Integer age;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 4, max = 24, message = "Length must be >= 4 and <= 24")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{4,24}$", message = "Should contain at least one uppercase letter and one lowercase letter and at least one number")
    private String password;
    private String phoneNumber;
    private UserImageDto avatar;

    @NotBlank
    @Pattern(regexp = "(?i)employer|applicant|admin", message = "Role must be 'Employer', 'Applicant' or 'Admin'")
    private String accountType;

    private Boolean enabled;
    private Long roleId;
}
