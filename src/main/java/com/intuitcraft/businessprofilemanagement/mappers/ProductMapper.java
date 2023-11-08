package com.intuitcraft.businessprofilemanagement.mappers;

import com.intuitcraft.businessprofilemanagement.entities.Product;
import com.intuitcraft.businessprofilemanagement.models.AddProductRequest;
import com.intuitcraft.businessprofilemanagement.models.ProductResponse;
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
