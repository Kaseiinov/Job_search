package com.example.home_work_49.exceptions.advice;

import com.example.home_work_49.exceptions.ErrorResponseBody;
import com.example.home_work_49.service.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final ErrorService errorService;

    @ExceptionHandler
    public ErrorResponseBody handleNoSuchElementException(NoSuchElementException e) {
        return errorService.makeResponse(e);
    }

    @ExceptionHandler
    public ErrorResponseBody handleSuchEmailAlreadyExists(Exception e) {
        return errorService.makeResponse(e);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseBody> validationHandler(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(errorService.makeResponse(e.getBindingResult()), HttpStatus.BAD_REQUEST);
    }
}
