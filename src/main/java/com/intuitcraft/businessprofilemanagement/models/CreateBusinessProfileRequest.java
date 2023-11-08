package com.intuitcraft.businessprofilemanagement.models;

import com.intuitcraft.businessprofilemanagement.enums.TaxIdentifier;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

@Data
@Builder
public class CreateBusinessProfileRequest {
    @NotBlank
    private String legalName;
    @NotBlank
    private String companyName;
    @NotNull
    @Valid
    private LegalAddress legalAddress;
    @NotNull
    @Valid
    private BusinessAddress businessAddress;
    @Email
    private String email;
    @URL
    private String website;
    @NotNull
    private Set<TaxIdentifier> taxIdentifiers;
}
