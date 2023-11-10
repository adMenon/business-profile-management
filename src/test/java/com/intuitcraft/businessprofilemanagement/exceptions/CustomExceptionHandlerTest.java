package com.intuitcraft.businessprofilemanagement.exceptions;

import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.intuitcraft.businessprofilemanagement.exceptions.fatal.CacheOperationException;
import com.intuitcraft.businessprofilemanagement.exceptions.fatal.FatalException;
import com.intuitcraft.businessprofilemanagement.exceptions.service.RevisionRejectedException;
import com.intuitcraft.businessprofilemanagement.exceptions.service.ServiceException;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomExceptionHandlerTest {
    @Mock
    private Logger log;

    @InjectMocks
    private CustomExceptionHandler customExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleServiceException_shouldReturnErrorResponseWithHttpStatus() {
        // Arrange
        ServiceException exception = new RevisionRejectedException("Service exception message", HttpStatus.INTERNAL_SERVER_ERROR, "Service exception info");

        // Act
        ResponseEntity<?> responseEntity = customExceptionHandler.handleServiceException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        // Add more assertions based on your GenericResponse structure
    }

    @Test
    void handleFatalException_shouldReturnErrorResponseWithHttpStatus() {
        // Arrange
        FatalException exception = new CacheOperationException("Fatal exception message", HttpStatus.INTERNAL_SERVER_ERROR, "Fatal exception info");

        // Act
        ResponseEntity<?> responseEntity = customExceptionHandler.handleFatalException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        // Add more assertions based on your GenericResponse structure
    }

    @Test
    void handleConflictExceptions_shouldReturnErrorResponseWithConflictHttpStatus() {
        // Arrange
        ResourceInUseException exception = new ResourceInUseException("Conflict exception message");

        // Act
        ResponseEntity<?> responseEntity = customExceptionHandler.handleConflictExceptions(exception);

        // Assert
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        // Add more assertions based on your GenericResponse structure
    }

}