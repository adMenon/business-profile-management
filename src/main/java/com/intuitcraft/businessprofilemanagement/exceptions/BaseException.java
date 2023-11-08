package com.intuitcraft.businessprofilemanagement.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseException extends RuntimeException {
    private final String[] info;
    private Exception exception;
    private HttpStatus httpStatus;

    protected BaseException(String message, String... info) {
        super(message);
        this.info = info;
    }

    protected BaseException(String message, Exception exception, String... info) {
        this(message, info);
        this.exception = exception;
    }

    protected BaseException(String message, HttpStatus httpStatus, String... info) {
        this(message, info);
        this.httpStatus = httpStatus;
    }

    protected BaseException(String message, Exception exception, HttpStatus httpStatus, String... info) {
        this(message, exception, info);
        this.httpStatus = httpStatus;
    }
}
