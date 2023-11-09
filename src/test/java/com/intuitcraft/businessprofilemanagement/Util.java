package com.intuitcraft.businessprofilemanagement;

import com.intuitcraft.businessprofilemanagement.entities.BusinessProfile;
import com.intuitcraft.businessprofilemanagement.entities.Product;
import com.intuitcraft.businessprofilemanagement.enums.TaxIdentifier;
import com.intuitcraft.businessprofilemanagement.models.Address;
import com.intuitcraft.businessprofilemanagement.models.AddressRequest;
import com.intuitcraft.businessprofilemanagement.models.CreateBusinessProfileRequest;
import com.intuitcraft.businessprofilemanagement.models.UpdateBusinessProfileRequest;

import java.util.HashSet;
import java.util.Set;

public class Util {
    public static Address getAddressDummy() {
        return Address.builder()
                .zip("zip")
                .state("state")
                .line1("line1")
                .line2("line2")
                .city("city")
                .country("country")
                .build();
    }

    public static AddressRequest getAddressRequestDummy() {
        return AddressRequest.builder()
                .zip("zip")
                .state("state")
                .line1("line1")
                .line2("line2")
                .city("city")
                .country("country")
                .build();
    }

    public static CreateBusinessProfileRequest getCreateBusinessProfileRequest() {
        return CreateBusinessProfileRequest.builder()
                .email("email@abc.com")
                .website("http://www.example.com")
                .legalName("legalName")
                .companyName("companyName")
                .taxIdentifiers(Set.of(TaxIdentifier.EIN))
                .businessAddress(getAddressRequestDummy())
                .legalAddress(getAddressRequestDummy())
                .build();
    }

    public static UpdateBusinessProfileRequest getUpdateBusinessProfileRequest() {
        return UpdateBusinessProfileRequest.builder()
                .email("email@abc.com")
                .website("http://www.example.com")
                .legalName("legalName")
                .companyName("companyName")
                .taxIdentifiers(Set.of(TaxIdentifier.EIN))
                .businessAddress(getAddressRequestDummy())
                .legalAddress(getAddressRequestDummy())
                .build();
    }

    public static BusinessProfile getDummyBusinessProfile() {
        return BusinessProfile.builder()
                .businessAddress(getAddressDummy())
                .legalAddress(getAddressDummy())
                .id("profile_id")
                .email("email@abc.com")
                .website("http://www.example.com")
                .legalName("legalName")
                .companyName("companyName")
                .taxIdentifiers(Set.of(TaxIdentifier.EIN))
                .subscribedProducts(new HashSet<>(Set.of("product_1")))
                .build();
    }

    public static Product getProductDummy() {
        return new Product("name", "url");
    }
}
