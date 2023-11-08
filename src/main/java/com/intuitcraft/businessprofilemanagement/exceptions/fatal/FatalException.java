package com.intuitcraft.businessprofilemanagement.exceptions.fatal;

import com.intuitcraft.businessprofilemanagement.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class FatalException extends BaseException {
    protected FatalException(String message, String... info) {
        super(message, info);
    }

    protected FatalException(String message, Exception exception, String... info) {
        super(message, exception, info);
    }

    protected FatalException(String message, HttpStatus httpStatus, String... info) {
        super(message, httpStatus, info);
    }

    protected FatalException(String message, Exception exception, HttpStatus httpStatus, String... info) {
        super(message, exception, httpStatus, info);
    }
}
