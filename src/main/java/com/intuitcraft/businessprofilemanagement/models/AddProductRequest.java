package com.intuitcraft.businessprofilemanagement.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class AddProductRequest {
    @NotBlank
    private String productId;
    @URL
    private String url;
}
