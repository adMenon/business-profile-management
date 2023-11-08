package com.intuitcraft.businessprofilemanagement.controller;

import com.intuitcraft.businessprofilemanagement.dto.CreateBusinessProfileRequest;
import com.intuitcraft.businessprofilemanagement.dto.UpdateBusinessProfileRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/business-profile")
public interface BusinessProfileController {
    @PostMapping("/create")
    ResponseEntity<?> create(@RequestBody CreateBusinessProfileRequest request);

    @PatchMapping("/{id}/update")
    ResponseEntity<?> update(@PathVariable String id,
                             @RequestBody UpdateBusinessProfileRequest request);

    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable String id);

    @PostMapping("/{id}/subscribe/{productId}")
    ResponseEntity<?> subscribe(@PathVariable String id, @PathVariable String productId);


    @PostMapping("/{id}/unsubscribe/{productId}")
    ResponseEntity<?> unsubscribe(@PathVariable String id, @PathVariable String productId);
}
