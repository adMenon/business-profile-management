package com.intuitcraft.businessprofilemanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/external-product-service/")
public interface ExternalProductController {
    @PostMapping("/{productId}")
    ResponseEntity<?> validate(@PathVariable String productId);

}
