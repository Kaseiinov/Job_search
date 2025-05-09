package com.example.home_work_49.service;

import com.example.home_work_49.dto.CategoryDto;
import com.example.home_work_49.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDto> getCategories();

    Optional<CategoryDto> findById(Long id);

    Optional<Category> findByIdModel(Long id);
}
