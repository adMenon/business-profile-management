package com.intuitcraft.businessprofilemanagement.models;

import com.intuitcraft.businessprofilemanagement.enums.TaxIdentifier;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBusinessProfileRequest {
    private String legalName;
    private String companyName;
    @Valid
    private AddressRequest legalAddress;
    @Valid
    private AddressRequest businessAddress;
    @Email
    private String email;
    @URL
    private String website;
    private Set<TaxIdentifier> taxIdentifiers;
}
