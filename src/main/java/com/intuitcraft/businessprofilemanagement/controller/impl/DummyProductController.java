package com.intuitcraft.businessprofilemanagement.controller.impl;

import com.intuitcraft.businessprofilemanagement.controller.ExternalProductController;
import com.intuitcraft.businessprofilemanagement.dto.ValidateProfileUpdateResponse;
import com.intuitcraft.businessprofilemanagement.enums.RevisionStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class DummyProductController implements ExternalProductController {

    private final Random random = new Random();

    @Override
    public ResponseEntity<?> validate(@PathVariable String productId) {
        double randomDouble = random.nextDouble();
        if (randomDouble < 0.95) {
            return ResponseEntity.ok(ValidateProfileUpdateResponse.builder()
                    .productId(productId)
                    .message("revision approved")
                    .revisionStatus(RevisionStatus.ACCEPTED)
                    .build());
        }
        return ResponseEntity.ok(ValidateProfileUpdateResponse.builder()
                .productId(productId)
                .message("revision rejected")
                .revisionStatus(RevisionStatus.REJECTED)
                .build());
    }
}
