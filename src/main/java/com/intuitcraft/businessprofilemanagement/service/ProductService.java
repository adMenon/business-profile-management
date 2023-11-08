package com.intuitcraft.businessprofilemanagement.service;

import com.intuitcraft.businessprofilemanagement.dto.AddProductRequest;
import com.intuitcraft.businessprofilemanagement.dto.ProductResponse;
import com.intuitcraft.businessprofilemanagement.dto.ValidateProfileUpdateResponse;
import com.intuitcraft.businessprofilemanagement.model.BusinessProfile;

import java.util.List;
import java.util.Set;

public interface ProductService {
    List<ValidateProfileUpdateResponse> validateProfileUpdate(BusinessProfile profileForUpdate,
                                                              Set<String> productIds);

    ProductResponse add(AddProductRequest request);

    Boolean remove(String productId);
}
