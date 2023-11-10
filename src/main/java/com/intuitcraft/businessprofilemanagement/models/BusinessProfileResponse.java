package com.intuitcraft.businessprofilemanagement.models;

import com.intuitcraft.businessprofilemanagement.enums.TaxIdentifier;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class BusinessProfileResponse {
    private String id;
    private String legalName;
    private String companyName;
    private Address legalAddress;
    private Address businessAddress;
    private String email;
    private String website;
    private Set<TaxIdentifier> taxIdentifiers;
    private Set<String> subscribedProducts;
}
