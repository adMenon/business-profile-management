package com.intuitcraft.businessprofilemanagement.mappers;

import com.intuitcraft.businessprofilemanagement.dto.BusinessProfileResponse;
import com.intuitcraft.businessprofilemanagement.dto.CreateBusinessProfileRequest;
import com.intuitcraft.businessprofilemanagement.dto.UpdateBusinessProfileRequest;
import com.intuitcraft.businessprofilemanagement.model.BusinessProfile;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class BusinessProfileMapper {
    public static BusinessProfileResponse mapToBusinessProfileResponse(BusinessProfile businessProfile) {
        return BusinessProfileResponse.builder()
                .address(businessProfile.getAddress())
                .id(businessProfile.getId())
                .website(businessProfile.getWebsite())
                .name(businessProfile.getName())
                .company(businessProfile.getCompany())
                .taxIdentifiers(businessProfile.getTaxIdentifiers())
                .email(businessProfile.getEmail())
                .subscribedBusiness(businessProfile.getSubscribedProducts())
                .build();
    }

    public static BusinessProfile mapToBusinessProfile(CreateBusinessProfileRequest request) {
        return BusinessProfile.builder()
                .address(request.getAddress())
                .email(request.getEmail())
                .name(request.getName())
                .website(request.getWebsite())
                .taxIdentifiers(request.getTaxIdentifiers())
                .company(request.getCompany())
                .build();
    }

    public static BusinessProfile mapToBusinessProfile(BusinessProfile profile,
                                                       UpdateBusinessProfileRequest request) {
        if (Objects.nonNull(request.getAddress())) {
            profile.setAddress(request.getAddress());
        }
        if (Objects.nonNull(request.getName())) {
            profile.setName(request.getName());
        }
        if (Objects.nonNull(request.getWebsite())) {
            profile.setWebsite(request.getWebsite());
        }
        if (Objects.nonNull(request.getCompany())) {
            profile.setCompany(request.getCompany());
        }
        if (Objects.nonNull(request.getEmail())) {
            profile.setEmail(request.getEmail());
        }
        if (Objects.nonNull(request.getTaxIdentifiers())) {
            profile.setTaxIdentifiers(request.getTaxIdentifiers());
        }
        return profile;
    }
}
