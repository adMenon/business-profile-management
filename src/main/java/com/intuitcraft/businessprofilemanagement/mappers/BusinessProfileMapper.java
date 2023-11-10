package com.intuitcraft.businessprofilemanagement.mappers;

import com.intuitcraft.businessprofilemanagement.entities.BusinessProfile;
import com.intuitcraft.businessprofilemanagement.models.Address;
import com.intuitcraft.businessprofilemanagement.models.AddressRequest;
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
                .legalAddress(businessProfile.getLegalAddress())
                .businessAddress(businessProfile.getBusinessAddress())
                .id(businessProfile.getId())
                .website(businessProfile.getWebsite())
                .legalName(businessProfile.getLegalName())
                .companyName(businessProfile.getCompanyName())
                .taxIdentifiers(businessProfile.getTaxIdentifiers())
                .email(businessProfile.getEmail())
                .subscribedProducts(Optional.ofNullable(businessProfile.getSubscribedProducts())
                        .orElse(Set.of()))
                .build();
    }


    public static BusinessProfile mapToBusinessProfile(CreateBusinessProfileRequest request) {
        return BusinessProfile.builder()
                .legalAddress(mapAddress(request.getLegalAddress()))
                .businessAddress(mapAddress(request.getBusinessAddress()))
                .email(request.getEmail())
                .legalName(request.getLegalName())
                .website(request.getWebsite())
                .taxIdentifiers(request.getTaxIdentifiers())
                .companyName(request.getCompanyName())
                .build();
    }

    private static Address mapAddress(AddressRequest addressRequest) {
        return Address.builder()
                .zip(addressRequest.getZip())
                .city(addressRequest.getCity())
                .line2(addressRequest.getLine2())
                .line1(addressRequest.getLine1())
                .state(addressRequest.getState())
                .country(addressRequest.getCountry())
                .build();

    }

    public static BusinessProfile mapToBusinessProfile(BusinessProfile profile,
                                                       UpdateBusinessProfileRequest request) {
        if (Objects.nonNull(request.getLegalAddress())) {
            profile.setLegalAddress(mapAddress(request.getLegalAddress()));
        }
        if (Objects.nonNull(request.getBusinessAddress())) {
            profile.setBusinessAddress(mapAddress(request.getBusinessAddress()));
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
