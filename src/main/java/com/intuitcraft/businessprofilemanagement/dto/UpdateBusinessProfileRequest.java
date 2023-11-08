package com.intuitcraft.businessprofilemanagement.dto;

import com.intuitcraft.businessprofilemanagement.enums.TaxIdentifier;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateBusinessProfileRequest {
    private String name;
    private String company;
    private String address;
    private String email;
    private String website;
    private Set<TaxIdentifier> taxIdentifiers;
}
