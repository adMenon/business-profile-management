package com.intuitcraft.businessprofilemanagement.exceptions.service;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends ServiceException {
    public ProductNotFoundException(String message, String... info) {
        super(message, info);
    }

    public ProductNotFoundException(String message, Exception exception, String... info) {
        super(message, exception, info);
    }

    public ProductNotFoundException(String message, HttpStatus httpStatus, String... info) {
        super(message, httpStatus, info);
    }

    public ProductNotFoundException(String message, Exception exception, HttpStatus httpStatus, String... info) {
        super(message, exception, httpStatus, info);
    }
}
