package com.intuitcraft.businessprofilemanagement.mappers;

import com.intuitcraft.businessprofilemanagement.dto.AddProductRequest;
import com.intuitcraft.businessprofilemanagement.dto.ProductResponse;
import com.intuitcraft.businessprofilemanagement.model.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {
    public static Product mapToProductRequest(AddProductRequest request) {
        return Product.builder()
                .id(request.getProductId())
                .url(request.getUrl())
                .build();
    }

    public static ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getId())
                .url(product.getUrl())
                .build();
    }
}
