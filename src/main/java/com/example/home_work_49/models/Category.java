package com.example.home_work_49.models;

import lombok.Data;

@Data
public class Category {
    private Long id;
    private String name;
    private Integer parentId;
}
