package com.example.home_work_49.exceptions;

import java.util.NoSuchElementException;

public class ImageNotFoundException extends NoSuchElementException {
    public ImageNotFoundException() {
        super("Image not found");
    }
}
