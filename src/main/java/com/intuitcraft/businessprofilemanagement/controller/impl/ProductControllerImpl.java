package com.intuitcraft.businessprofilemanagement.controller.impl;

import com.intuitcraft.businessprofilemanagement.controller.ProductController;
import com.intuitcraft.businessprofilemanagement.dto.AddProductRequest;
import com.intuitcraft.businessprofilemanagement.dto.GenericResponse;
import com.intuitcraft.businessprofilemanagement.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductServiceImpl productService;

    @Override
    public ResponseEntity<?> add(AddProductRequest request) {
        return GenericResponse.ok(productService.add(request));
    }

    @Override
    public ResponseEntity<?> remove(String productId) {
        return GenericResponse.ok(productService.remove(productId));
    }
}
