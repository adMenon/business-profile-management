package com.intuitcraft.businessprofilemanagement.exceptions.service;

import org.springframework.http.HttpStatus;

public class ProfileNotFoundException extends ServiceException {
    public ProfileNotFoundException(String message, String... info) {
        super(message, info);
    }

    public ProfileNotFoundException(String message, Exception exception, String... info) {
        super(message, exception, info);
    }

    public ProfileNotFoundException(String message, HttpStatus httpStatus, String... info) {
        super(message, httpStatus, info);
    }

    public ProfileNotFoundException(String message, Exception exception, HttpStatus httpStatus, String... info) {
        super(message, exception, httpStatus, info);
    }
}
