package com.intuitcraft.businessprofilemanagement.controller;

import com.intuitcraft.businessprofilemanagement.models.AddProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/product")
public interface ProductController {
    @PostMapping("/add")
    ResponseEntity<?> add(@RequestBody AddProductRequest request);

    @DeleteMapping("/remove/{productId}")
    ResponseEntity<?> remove(@PathVariable String productId);

    @GetMapping
    ResponseEntity<?> getAll();
}
