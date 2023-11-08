package com.intuitcraft.businessprofilemanagement.dto;

import com.intuitcraft.businessprofilemanagement.enums.TaxIdentifier;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class BusinessProfileResponse {
    private String id;
    private String name;
    private String company;
    private String address;
    private String email;
    private String website;
    private Set<TaxIdentifier> taxIdentifiers;
    private Set<String> subscribedBusiness;
}
