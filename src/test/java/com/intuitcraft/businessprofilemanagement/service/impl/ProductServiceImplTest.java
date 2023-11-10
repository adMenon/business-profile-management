package com.intuitcraft.businessprofilemanagement.service.impl;

import com.intuitcraft.businessprofilemanagement.Util;
import com.intuitcraft.businessprofilemanagement.entities.Product;
import com.intuitcraft.businessprofilemanagement.enums.RevisionStatus;
import com.intuitcraft.businessprofilemanagement.models.AddProductRequest;
import com.intuitcraft.businessprofilemanagement.models.ProductResponse;
import com.intuitcraft.businessprofilemanagement.models.ValidateProfileUpdateResponse;
import com.intuitcraft.businessprofilemanagement.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

import static com.intuitcraft.businessprofilemanagement.Util.getProductDummy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {

    private ProductServiceImpl productService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productService = new ProductServiceImpl(restTemplate, productRepository);
    }

    @Test
    void validateProfileUpdate() {
        when(productRepository.findById(any(String.class)))
                .thenReturn(new Product("product_id", "url.com"));
        List<ValidateProfileUpdateResponse> responseList =
                productService.validateProfileUpdate(Util.getDummyBusinessProfile(), Set.of("xyz"));
        verify(productRepository, times(1)).findById(anyString());
    }

    @Test
    void validateProfileUpdateProductNotFound() {
        when(productRepository.findById(any(String.class)))
                .thenReturn(null);
        List<ValidateProfileUpdateResponse> responseList =
                productService.validateProfileUpdate(Util.getDummyBusinessProfile(), Set.of("xyz"));
        assertEquals(1, responseList.size());
        assertEquals(RevisionStatus.UNAVAILABLE, responseList.get(0).getRevisionStatus());
        verify(productRepository, times(1)).findById(anyString());
    }

    @Test
    void add() {
        when(productRepository.save(any(Product.class)))
                .thenReturn(new Product("product_id", "url.com"));
        ProductResponse response = productService.add(AddProductRequest.builder()
                .productId("product_id")
                .url("url.com")
                .build());
        assertNotNull(response);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void remove() {
        doNothing().when(productRepository).delete(anyString());
        assertNotNull(productService.remove("product_id"));
        verify(productRepository, times(1)).delete(anyString());

    }

    @Test
    void findAll() {
        when(productRepository.findAll()).thenReturn(List.of(getProductDummy()));
        List<ProductResponse> productResponses = productService.findAll();
        assertNotNull(productResponses);
        verify(productRepository, times(1)).findAll();

    }
}