package com.example.home_work_49.exceptions;

public class SuchEmailAlreadyExistsException extends Exception {
    public SuchEmailAlreadyExistsException() {
        super("Such email already exists");
    }
}
