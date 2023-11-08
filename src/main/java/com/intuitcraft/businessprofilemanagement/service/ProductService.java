package com.intuitcraft.businessprofilemanagement.service;

import com.intuitcraft.businessprofilemanagement.models.AddProductRequest;
import com.intuitcraft.businessprofilemanagement.models.ProductResponse;
import com.intuitcraft.businessprofilemanagement.models.ValidateProfileUpdateResponse;
import com.intuitcraft.businessprofilemanagement.entities.BusinessProfile;

import java.util.List;
import java.util.Set;

public interface ProductService {
    List<ValidateProfileUpdateResponse> validateProfileUpdate(BusinessProfile profileForUpdate,
                                                              Set<String> productIds);

    ProductResponse add(AddProductRequest request);

    Boolean remove(String productId);
}
