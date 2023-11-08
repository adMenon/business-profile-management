package com.intuitcraft.businessprofilemanagement.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequest {
    @NotBlank
    private String productId;
    @URL
    private String url;
}
