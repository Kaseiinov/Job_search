package com.example.home_work_49.service.impl;

import com.example.home_work_49.dao.CategoryDao;
import com.example.home_work_49.dto.CategoryDto;
import com.example.home_work_49.models.Category;
import com.example.home_work_49.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao;

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = categoryDao.getCategories();

        return categoryBuilder(categories);
    }

    public List<CategoryDto> categoryBuilder(List<Category> categoryList){
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
}
