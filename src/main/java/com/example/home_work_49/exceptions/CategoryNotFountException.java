package com.example.home_work_49.exceptions;

import java.util.NoSuchElementException;

public class CategoryNotFountException extends NoSuchElementException {
    public CategoryNotFountException() {
        super("Category not found");
    }
}
