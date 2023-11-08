package com.intuitcraft.businessprofilemanagement.service;

import com.intuitcraft.businessprofilemanagement.models.BusinessProfileResponse;
import com.intuitcraft.businessprofilemanagement.models.CreateBusinessProfileRequest;
import com.intuitcraft.businessprofilemanagement.models.UpdateBusinessProfileRequest;

public interface BusinessProfileService {
    BusinessProfileResponse create(CreateBusinessProfileRequest request);

    BusinessProfileResponse findById(String id);

    BusinessProfileResponse update(String id, UpdateBusinessProfileRequest request);

    Boolean subscribe(String id, String productId);

    Boolean unsubscribe(String id, String productId);
}
