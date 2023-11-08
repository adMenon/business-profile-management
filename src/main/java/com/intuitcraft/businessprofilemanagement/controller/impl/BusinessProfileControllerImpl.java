package com.intuitcraft.businessprofilemanagement.controller.impl;

import com.intuitcraft.businessprofilemanagement.controller.BusinessProfileController;
import com.intuitcraft.businessprofilemanagement.dto.CreateBusinessProfileRequest;
import com.intuitcraft.businessprofilemanagement.dto.GenericResponse;
import com.intuitcraft.businessprofilemanagement.dto.UpdateBusinessProfileRequest;
import com.intuitcraft.businessprofilemanagement.service.impl.BusinessProfileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BusinessProfileControllerImpl implements BusinessProfileController {
    private final BusinessProfileServiceImpl businessProfileService;

    @Override
    public ResponseEntity<?> create(CreateBusinessProfileRequest request) {
        return GenericResponse.with(businessProfileService.create(request), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> update(String id, UpdateBusinessProfileRequest request) {
        return GenericResponse.ok(businessProfileService.update(id, request));
    }

    @Override
    public ResponseEntity<?> findById(String id) {
        return GenericResponse.ok(businessProfileService.findById(id));
    }

    @Override
    public ResponseEntity<?> subscribe(String id, String productId) {
        return GenericResponse.ok(businessProfileService.subscribe(id, productId));
    }

    @Override
    public ResponseEntity<?> unsubscribe(String id, String productId) {
        return GenericResponse.ok(businessProfileService.unsubscribe(id, productId));
    }
}
