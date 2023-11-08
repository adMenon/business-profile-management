package com.intuitcraft.businessprofilemanagement.exceptions.service;

import com.intuitcraft.businessprofilemanagement.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class ServiceException extends BaseException {
    protected ServiceException(String message, String... info) {
        super(message, info);
    }

    protected ServiceException(String message, Exception exception, String... info) {
        super(message, exception, info);
    }

    protected ServiceException(String message, HttpStatus httpStatus, String... info) {
        super(message, httpStatus, info);
    }

    protected ServiceException(String message, Exception exception, HttpStatus httpStatus, String... info) {
        super(message, exception, httpStatus, info);
    }
}
