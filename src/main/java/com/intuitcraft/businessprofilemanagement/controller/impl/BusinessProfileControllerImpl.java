package com.intuitcraft.businessprofilemanagement.controller.impl;

import com.intuitcraft.businessprofilemanagement.controller.BusinessProfileController;
import com.intuitcraft.businessprofilemanagement.models.CreateBusinessProfileRequest;
import com.intuitcraft.businessprofilemanagement.models.GenericResponse;
import com.intuitcraft.businessprofilemanagement.models.UpdateBusinessProfileRequest;
import com.intuitcraft.businessprofilemanagement.service.impl.BusinessProfileServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BusinessProfileControllerImpl implements BusinessProfileController {
    private final BusinessProfileServiceImpl businessProfileService;

    @Override
    public ResponseEntity<?> create(@NotNull @Valid CreateBusinessProfileRequest request) {
        return GenericResponse.with(businessProfileService.create(request), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> update(@NotBlank String id, @NotNull @Valid UpdateBusinessProfileRequest request) {
        return GenericResponse.ok(businessProfileService.update(id, request));
    }

    @Override
    public ResponseEntity<?> findById(@NotBlank String id) {
        return GenericResponse.ok(businessProfileService.findById(id));
    }

    @Override
    public ResponseEntity<?> subscribe(@NotBlank String id, @NotBlank String productId) {
        return GenericResponse.ok(businessProfileService.subscribe(id, productId));
    }

    @Override
    public ResponseEntity<?> unsubscribe(@NotBlank String id, @NotBlank String productId) {
        return GenericResponse.ok(businessProfileService.unsubscribe(id, productId));
    }
}
