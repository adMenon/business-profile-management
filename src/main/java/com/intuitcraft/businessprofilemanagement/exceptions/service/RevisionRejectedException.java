package com.intuitcraft.businessprofilemanagement.exceptions.service;


import org.springframework.http.HttpStatus;

public class RevisionRejectedException extends ServiceException {
    public RevisionRejectedException(String message, String... info) {
        super(message, info);
    }

    public RevisionRejectedException(String message, Exception exception, String... info) {
        super(message, exception, info);
    }

    public RevisionRejectedException(String message, HttpStatus httpStatus, String... info) {
        super(message, httpStatus, info);
    }

    public RevisionRejectedException(String message, Exception exception, HttpStatus httpStatus, String... info) {
        super(message, exception, httpStatus, info);
    }

}
