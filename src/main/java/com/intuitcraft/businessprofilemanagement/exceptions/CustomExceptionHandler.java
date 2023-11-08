package com.intuitcraft.businessprofilemanagement.exceptions;

import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.intuitcraft.businessprofilemanagement.exceptions.fatal.FatalException;
import com.intuitcraft.businessprofilemanagement.exceptions.service.ServiceException;
import com.intuitcraft.businessprofilemanagement.models.ErrorResponse;
import com.intuitcraft.businessprofilemanagement.models.GenericResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleServiceException(ServiceException exception) {
        log.error("[SERVICE] Error encountered with message: {}, info: {}",
                exception.getMessage(), exception.getInfo(), exception);
        return GenericResponse.error(
                new ErrorResponse(exception.getMessage(), exception.getInfo()),
                exception.getHttpStatus());
    }

    @ExceptionHandler(FatalException.class)
    public ResponseEntity<?> handleFatalException(FatalException exception) {
        log.error("[FATAL] Error encountered with message: {}, info: {}",
                exception.getMessage(), exception.getInfo(), exception);


        return GenericResponse.error(
                new ErrorResponse(exception.getMessage(), exception.getInfo()),
                exception.getHttpStatus());
    }

    @ExceptionHandler(ResourceInUseException.class)
    public ResponseEntity<?> handleConflictExceptions(ResourceInUseException exception) {
        log.error("[FATAL] Error encountered with message: {}",
                exception.getMessage(), exception);


        return GenericResponse.error(
                new ErrorResponse(exception.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentViolationException(
            MethodArgumentNotValidException exception) {

        log.error("[FATAL] Error encountered with message: {}, info: {}",
                exception.getMessage(), exception.getCause(), exception);


        return GenericResponse.error(new ErrorResponse("Constraint violation found",
                        exception.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(e -> e.getField() + ":" + e.getDefaultMessage())
                                .toArray(String[]::new)),
                HttpStatus.BAD_REQUEST);
    }


}
