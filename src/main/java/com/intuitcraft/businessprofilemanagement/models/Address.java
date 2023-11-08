package com.intuitcraft.businessprofilemanagement.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @NotBlank
    private String line1;
    @NotBlank
    private String line2;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String country;
    @NotBlank
    private String zip;

    public String getAddressString() {
        return line1 + ", " + line2 + ", " + city + ", " + state + ", " + country + "-" + zip;
    }

}