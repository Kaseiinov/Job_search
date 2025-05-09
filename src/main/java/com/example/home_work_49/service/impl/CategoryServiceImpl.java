package com.example.home_work_49.service.impl;

import com.example.home_work_49.dto.CategoryDto;
import com.example.home_work_49.exceptions.CategoryNotFountException;
import com.example.home_work_49.models.Category;
import com.example.home_work_49.repository.CategoryRepository;
import com.example.home_work_49.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categoryListBuilder(categories);
    }

    @Override
    public Optional<CategoryDto> findById(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFountException::new);
        return Optional.of(categoryBuilder(category));
    }

    @Override
    public Optional<Category> findByIdModel(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFountException::new);
        return Optional.of(category);
    }

    public List<CategoryDto> categoryListBuilder(List<Category> categoryList){
        return categoryList
                .stream()
                .map(e -> CategoryDto
                        .builder()
                        .id(e.getId())
                        .name(e.getName())
                        .parentId(e.getParentId())
                        .build())
                .toList();
    }

    public CategoryDto categoryBuilder(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .parentId(category.getParentId())
                .build();
    }
}
