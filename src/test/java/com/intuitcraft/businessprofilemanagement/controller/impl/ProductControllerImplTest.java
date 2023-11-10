package com.intuitcraft.businessprofilemanagement.controller.impl;

import com.intuitcraft.businessprofilemanagement.models.AddProductRequest;
import com.intuitcraft.businessprofilemanagement.models.ProductResponse;
import com.intuitcraft.businessprofilemanagement.service.impl.ProductServiceImpl;
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

class ProductControllerImplTest {

    @Mock
    private ProductServiceImpl productService;

    @InjectMocks
    private ProductControllerImpl productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void add_shouldReturnOkResponseEntity() {
        // Arrange
        AddProductRequest request = new AddProductRequest("productId", "url");
        when(productService.add(request)).thenReturn(ProductResponse.builder().build());

        // Act
        ResponseEntity<?> responseEntity = productController.add(request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Add more assertions based on your GenericResponse structure
    }

    @Test
    void remove_shouldReturnOkResponseEntity() {
        // Arrange
        String productId = "testProductId";
        when(productService.remove(eq(productId))).thenReturn(true);

        // Act
        ResponseEntity<?> responseEntity = productController.remove(productId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Add more assertions based on your GenericResponse structure
    }

    @Test
    void getAll_shouldReturnOkResponseEntity() {
        // Arrange
        when(productService.findAll()).thenReturn(List.of(ProductResponse.builder().build()));

        // Act
        ResponseEntity<?> responseEntity = productController.getAll();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Add more assertions based on your GenericResponse structure
    }
}