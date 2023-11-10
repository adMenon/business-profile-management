package com.intuitcraft.businessprofilemanagement.controller.impl;

import com.intuitcraft.businessprofilemanagement.controller.ProductController;
import com.intuitcraft.businessprofilemanagement.models.AddProductRequest;
import com.intuitcraft.businessprofilemanagement.models.GenericResponse;
import com.intuitcraft.businessprofilemanagement.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductServiceImpl productService;

    @Override
    public ResponseEntity<?> add(@NotNull @Valid AddProductRequest request) {
        return GenericResponse.ok(productService.add(request));
    }

    @Override
    public ResponseEntity<?> remove(@NotBlank String productId) {
        return GenericResponse.ok(productService.remove(productId));
    }

    @Override
    public ResponseEntity<?> getAll() {
        return GenericResponse.ok(productService.findAll());
    }
}
