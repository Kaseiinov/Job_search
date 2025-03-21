package com.example.home_work_49.models;

import lombok.Data;

@Data
public class RespondedApplicant {
    private Long id;
    private Long resumeId;
    private Long vacancyId;
    private boolean confirmation;
}