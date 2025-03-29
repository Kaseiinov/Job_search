package com.example.home_work_49.exceptions;

import java.util.NoSuchElementException;

public class VacancyNotFoundException extends NoSuchElementException {
    public VacancyNotFoundException() {
        super("Vacancy not found: " );
    }
}
