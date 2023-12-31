package com.intuitcraft.businessprofilemanagement.service;

import com.intuitcraft.businessprofilemanagement.models.BusinessProfileResponse;
import com.intuitcraft.businessprofilemanagement.models.CreateBusinessProfileRequest;
import com.intuitcraft.businessprofilemanagement.models.UpdateBusinessProfileRequest;

import java.util.List;

public interface BusinessProfileService {
    String create(CreateBusinessProfileRequest request);

    BusinessProfileResponse findById(String id);

    Boolean update(String id, UpdateBusinessProfileRequest request);

    Boolean subscribe(String id, String productId);

    Boolean unsubscribe(String id, String productId);

    List<BusinessProfileResponse> findAll();
}
