package com.intuitcraft.businessprofilemanagement.controller.impl;

import com.intuitcraft.businessprofilemanagement.Util;
import com.intuitcraft.businessprofilemanagement.models.CreateBusinessProfileRequest;
import com.intuitcraft.businessprofilemanagement.models.UpdateBusinessProfileRequest;
import com.intuitcraft.businessprofilemanagement.service.impl.BusinessProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class BusinessProfileControllerImplTest {

    @Mock
    private BusinessProfileServiceImpl businessProfileService;

    @InjectMocks
    private BusinessProfileControllerImpl businessProfileController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldReturnCreatedResponseEntity() {
        // Arrange
        CreateBusinessProfileRequest request = Util.getCreateBusinessProfileRequest();
        when(businessProfileService.create(request)).thenReturn("profile_id");

        // Act
        ResponseEntity<?> responseEntity = businessProfileController.create(request);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        // Add more assertions based on your GenericResponse structure
    }

    @Test
    void update_shouldReturnOkResponseEntity() {
        // Arrange
        String id = "testId";
        UpdateBusinessProfileRequest request = Util.getUpdateBusinessProfileRequest();
        when(businessProfileService.update(eq(id), eq(request))).thenReturn(true);

        // Act
        ResponseEntity<?> responseEntity = businessProfileController.update(id, request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Add more assertions based on your GenericResponse structure
    }

    @Test
    void findById_shouldReturnOkResponseEntity() {
        // Arrange
        String id = "testId";
        when(businessProfileService.findById(eq(id))).thenReturn(Util.getDummyBusinessProfileResponse());

        // Act
        ResponseEntity<?> responseEntity = businessProfileController.findById(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Add more assertions based on your GenericResponse structure
    }

    @Test
    void findAll_shouldReturnOkResponseEntity() {
        // Arrange
        when(businessProfileService.findAll()).thenReturn(List.of(Util.getDummyBusinessProfileResponse()));

        // Act
        ResponseEntity<?> responseEntity = businessProfileController.findAll();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Add more assertions based on your GenericResponse structure
    }

    @Test
    void subscribe_shouldReturnOkResponseEntity() {
        // Arrange
        String id = "testId";
        String productId = "testProductId";
        when(businessProfileService.subscribe(eq(id), eq(productId))).thenReturn(true);

        // Act
        ResponseEntity<?> responseEntity = businessProfileController.subscribe(id, productId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Add more assertions based on your GenericResponse structure
    }

    @Test
    void unsubscribe_shouldReturnOkResponseEntity() {
        // Arrange
        String id = "testId";
        String productId = "testProductId";
        when(businessProfileService.unsubscribe(eq(id), eq(productId))).thenReturn(true);

        // Act
        ResponseEntity<?> responseEntity = businessProfileController.unsubscribe(id, productId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Add more assertions based on your GenericResponse structure
    }
}