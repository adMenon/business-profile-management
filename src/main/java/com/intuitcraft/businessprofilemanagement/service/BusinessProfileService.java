package com.intuitcraft.businessprofilemanagement.service;

import com.intuitcraft.businessprofilemanagement.dto.BusinessProfileResponse;
import com.intuitcraft.businessprofilemanagement.dto.CreateBusinessProfileRequest;
import com.intuitcraft.businessprofilemanagement.dto.UpdateBusinessProfileRequest;

public interface BusinessProfileService {
    BusinessProfileResponse create(CreateBusinessProfileRequest request);

    BusinessProfileResponse findById(String id);

    BusinessProfileResponse update(String id, UpdateBusinessProfileRequest request);

    Boolean subscribe(String id, String productId);

    Boolean unsubscribe(String id, String productId);
}
