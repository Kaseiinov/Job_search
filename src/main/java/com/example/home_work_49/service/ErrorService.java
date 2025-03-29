package com.example.home_work_49.service;

import com.example.home_work_49.exceptions.ErrorResponseBody;
import org.springframework.validation.BindingResult;

import java.util.NoSuchElementException;

public interface ErrorService {
    ErrorResponseBody makeResponse(NoSuchElementException e);

    ErrorResponseBody makeResponse(Exception e);

    ErrorResponseBody makeResponse(BindingResult bindingResult);
}
