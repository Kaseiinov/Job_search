package com.example.home_work_49.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Vacancy {
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
