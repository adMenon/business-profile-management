package com.intuitcraft.businessprofilemanagement.mappers;

import com.intuitcraft.businessprofilemanagement.entities.BusinessProfile;
import com.intuitcraft.businessprofilemanagement.models.BusinessProfileResponse;
import com.intuitcraft.businessprofilemanagement.models.CreateBusinessProfileRequest;
import com.intuitcraft.businessprofilemanagement.models.UpdateBusinessProfileRequest;
import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@UtilityClass
public class BusinessProfileMapper {
    public static BusinessProfileResponse mapToBusinessProfileResponse(BusinessProfile businessProfile) {
        return BusinessProfileResponse.builder()
                .legalAddress(businessProfile.getLegalAddress().getAddressString())
                .businessAddress(businessProfile.getBusinessAddress().getAddressString())
                .id(businessProfile.getId())
                .website(businessProfile.getWebsite())
                .legalName(businessProfile.getLegalName())
                .companyName(businessProfile.getCompanyName())
                .taxIdentifiers(businessProfile.getTaxIdentifiers())
                .email(businessProfile.getEmail())
                .subscribedBusiness(Optional.ofNullable(businessProfile.getSubscribedProducts())
                        .orElse(Set.of()))
                .build();
    }


    public static BusinessProfile mapToBusinessProfile(CreateBusinessProfileRequest request) {
        return BusinessProfile.builder()
                .legalAddress(request.getLegalAddress())
                .businessAddress(request.getBusinessAddress())
                .email(request.getEmail())
                .legalName(request.getLegalName())
                .website(request.getWebsite())
                .taxIdentifiers(request.getTaxIdentifiers())
                .companyName(request.getCompanyName())
                .build();
    }

    public static BusinessProfile mapToBusinessProfile(BusinessProfile profile,
                                                       UpdateBusinessProfileRequest request) {
        if (Objects.nonNull(request.getLegalAddress())) {
            profile.setLegalAddress(request.getLegalAddress());
        }
        if (Objects.nonNull(request.getBusinessAddress())) {
            profile.setBusinessAddress(request.getBusinessAddress());
        }
        if (Objects.nonNull(request.getCompanyName())) {
            profile.setCompanyName(request.getCompanyName());
        }
        if (Objects.nonNull(request.getLegalName())) {
            profile.setLegalName(request.getLegalName());
        }
        if (Objects.nonNull(request.getWebsite())) {
            profile.setWebsite(request.getWebsite());
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
