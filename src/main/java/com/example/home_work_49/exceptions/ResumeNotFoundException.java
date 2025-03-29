package com.example.home_work_49.exceptions;

import java.util.NoSuchElementException;

public class ResumeNotFoundException extends NoSuchElementException {
    public ResumeNotFoundException() {
        super("Resume not found");
    }
}
