package com.intuitcraft.businessprofilemanagement.fallback;

import com.intuitcraft.businessprofilemanagement.models.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProfileRevisionVerification {
    public ResponseEntity<?> fallback() {
        return GenericResponse.error(Map.of(
                "error", true,
                "isRetryable", true,
                "message", "Under Maintenance"
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
