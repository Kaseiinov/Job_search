package com.example.home_work_49.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Resume {
    private Long id;
    private Long applicantId;
    private String name;
    private Long categoryId;
    private double salary;
    private boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
}
