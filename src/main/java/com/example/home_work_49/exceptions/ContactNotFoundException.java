package com.example.home_work_49.exceptions;

import java.util.NoSuchElementException;

public class ContactNotFoundException extends NoSuchElementException {
    public ContactNotFoundException() {
        super("Contact not found");
    }
}
