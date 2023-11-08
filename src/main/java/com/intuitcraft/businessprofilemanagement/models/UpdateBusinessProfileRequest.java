package com.intuitcraft.businessprofilemanagement.models;

import com.intuitcraft.businessprofilemanagement.enums.TaxIdentifier;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

@Data
public class UpdateBusinessProfileRequest {
    private String legalName;
    private String companyName;
    private LegalAddress legalAddress;
    private BusinessAddress businessAddress;
    @Email
    private String email;
    @URL
    private String website;
    private Set<TaxIdentifier> taxIdentifiers;
}
