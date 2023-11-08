package com.intuitcraft.businessprofilemanagement.exceptions.fatal;

import org.springframework.http.HttpStatus;

public class CacheOperationException extends FatalException {

    public CacheOperationException(String message, String... info) {
        super(message, info);
    }

    public CacheOperationException(String message, Exception exception, String... info) {
        super(message, exception, info);
    }

    public CacheOperationException(String message, HttpStatus httpStatus, String... info) {
        super(message, httpStatus, info);
    }

    public CacheOperationException(String message, Exception exception, HttpStatus httpStatus, String... info) {
        super(message, exception, httpStatus, info);
    }
}
