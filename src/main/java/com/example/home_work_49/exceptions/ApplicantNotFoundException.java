package com.example.home_work_49.exceptions;

import java.util.NoSuchElementException;

public class ApplicantNotFoundException extends NoSuchElementException {
    public ApplicantNotFoundException() {
        super("Applicant not found");
    }
}
