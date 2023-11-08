package com.intuitcraft.businessprofilemanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private String productId;
    private String url;
}