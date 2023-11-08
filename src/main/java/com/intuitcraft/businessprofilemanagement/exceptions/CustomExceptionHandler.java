package com.intuitcraft.businessprofilemanagement.exceptions;

import com.intuitcraft.businessprofilemanagement.dto.ErrorResponse;
import com.intuitcraft.businessprofilemanagement.dto.GenericResponse;
import com.intuitcraft.businessprofilemanagement.exceptions.service.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleServiceException(ServiceException exception) {
        log.error("[SERVICE] Error encountered with message: {}, info: {}, exception :",
                exception.getMessage(), exception.getInfo(), exception);
        return GenericResponse.error(
                new ErrorResponse(exception.getMessage(), exception.getInfo()),
                exception.getHttpStatus());
    }
}
