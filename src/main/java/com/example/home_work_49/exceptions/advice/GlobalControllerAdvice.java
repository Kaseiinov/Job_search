package com.example.home_work_49.exceptions.advice;

import com.example.home_work_49.exceptions.ErrorResponseBody;
import com.example.home_work_49.exceptions.SuchEmailAlreadyExistsException;
import com.example.home_work_49.exceptions.UserNotFoundException;
import com.example.home_work_49.service.ErrorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.security.access.AccessDeniedException;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final ErrorService errorService;

    @ExceptionHandler({NoSuchElementException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(Exception ex, Model model, HttpServletRequest request) {
        log.warn("Resource not found: {}", request.getRequestURI(), ex);

        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("exception", ex.getClass().getSimpleName());

        return "errors/error";
    }

    @ExceptionHandler(SuchEmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleSuchEmailAlreadyExists(SuchEmailAlreadyExistsException ex, Model model, HttpServletRequest request) {
        log.warn("Email conflict: {}", ex.getMessage());

        model.addAttribute("status", HttpStatus.CONFLICT.value());
        model.addAttribute("reason", HttpStatus.CONFLICT.getReasonPhrase());
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("path", request.getRequestURI());

        return "errors/error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseBody> validationHandler(MethodArgumentNotValidException e) {
        log.warn("Validation error: {}", e.getMessage());
        return new ResponseEntity<>(errorService.makeResponse(e.getBindingResult()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDeniedException(AccessDeniedException ex, Model model, HttpServletRequest request) {
        log.warn("Access denied for: {}", request.getRequestURI(), ex);

        model.addAttribute("status", HttpStatus.FORBIDDEN.value());
        model.addAttribute("reason", "Access Denied");
        model.addAttribute("message", "You don't have permission to access this resource");
        model.addAttribute("path", request.getRequestURI());

        return "errors/error";
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleNullPointerException(NullPointerException ex, Model model, HttpServletRequest request) {
        log.error("Null pointer exception at: {}", request.getRequestURI(), ex);

        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("reason", "Internal Server Error");
        model.addAttribute("message", "A null pointer exception occurred");
        model.addAttribute("path", request.getRequestURI());

        return "errors/error";
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleRuntimeException(RuntimeException ex, Model model, HttpServletRequest request) {
        log.error("Runtime exception at: {}", request.getRequestURI(), ex);

        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("reason", "Internal Server Error");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("exception", ex.getClass().getSimpleName());

        return "errors/error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception ex, Model model, HttpServletRequest request) {
        log.error("Unhandled exception at: {}", request.getRequestURI(), ex);

        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("reason", "Internal Server Error");
        model.addAttribute("message", "An unexpected error occurred");
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("exception", ex.getClass().getSimpleName());

        return "errors/error";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoHandlerFoundException(NoHandlerFoundException ex, Model model, HttpServletRequest request) {
        log.warn("Page not found: {}", request.getRequestURI());

        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", "Page Not Found");
        model.addAttribute("message", "The requested page was not found");
        model.addAttribute("path", request.getRequestURI());

        return "errors/error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model, HttpServletRequest request) {
        log.warn("Illegal argument: {}", ex.getMessage());

        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", "Bad Request");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("path", request.getRequestURI());

        return "errors/error";
    }

    @ExceptionHandler({IllegalStateException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public String handleIllegalStateException(IllegalStateException ex, Model model, HttpServletRequest request) {
        log.error("Service unavailable: {}", ex.getMessage());

        model.addAttribute("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        model.addAttribute("reason", "Service Unavailable");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("path", request.getRequestURI());

        return "errors/error";
    }
}