package com.example.home_work_49.exceptions;

import java.util.NoSuchElementException;

public class PublicationNotFoundException extends NoSuchElementException {
    public PublicationNotFoundException() {
        super("Publication not found");
    }
}
