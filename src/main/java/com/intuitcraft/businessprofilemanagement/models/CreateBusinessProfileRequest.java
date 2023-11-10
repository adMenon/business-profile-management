package com.intuitcraft.businessprofilemanagement.models;

import com.intuitcraft.businessprofilemanagement.enums.TaxIdentifier;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    private AddressRequest legalAddress;
    @NotNull
    @Valid
    private AddressRequest businessAddress;
    @Email
    private String email;
    @URL
    private String website;
    @NotNull
    private Set<TaxIdentifier> taxIdentifiers;
}
