package com.example.home_work_49.exceptions;

import java.util.NoSuchElementException;

public class EmployerNotFounException extends NoSuchElementException {
    public EmployerNotFounException() {
        super("Employer not found");
    }
}
