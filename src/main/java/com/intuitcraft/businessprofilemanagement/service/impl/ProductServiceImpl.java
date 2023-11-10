package com.intuitcraft.businessprofilemanagement.service.impl;

import com.intuitcraft.businessprofilemanagement.entities.BusinessProfile;
import com.intuitcraft.businessprofilemanagement.entities.Product;
import com.intuitcraft.businessprofilemanagement.enums.RevisionStatus;
import com.intuitcraft.businessprofilemanagement.exceptions.service.ProductNotFoundException;
import com.intuitcraft.businessprofilemanagement.mappers.ProductMapper;
import com.intuitcraft.businessprofilemanagement.models.AddProductRequest;
import com.intuitcraft.businessprofilemanagement.models.ProductResponse;
import com.intuitcraft.businessprofilemanagement.models.ValidateProfileUpdateResponse;
import com.intuitcraft.businessprofilemanagement.repository.ProductRepository;
import com.intuitcraft.businessprofilemanagement.service.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final RestTemplate restTemplate;
    private final ProductRepository productRepository;

    @Override
    public List<ValidateProfileUpdateResponse> validateProfileUpdate(BusinessProfile profileForUpdate,
                                                                     Set<String> productIds) {

        List<ValidateProfileUpdateResponse> validationResponses = new ArrayList<>();
        List<CompletableFuture<Boolean>> productValidationFutures =
                productIds.stream().map(productId -> CompletableFuture.supplyAsync(() -> {
                            Product product = productRepository.findById(productId);
                            if (Objects.isNull(product)) {
                                throw new ProductNotFoundException("Product with id :" + productId + "not found",
                                        HttpStatus.NOT_FOUND);
                            }
                            return invokeValidateCall(product.getUrl(), profileForUpdate);
                        }).exceptionallyAsync(ex -> {
                            log.error("Exception occurred while verifying", ex);
                            return ValidateProfileUpdateResponse.builder()
                                    .productId(productId)
                                    .revisionStatus(RevisionStatus.UNAVAILABLE)
                                    .message("")
                                    .build();
                        }).thenApplyAsync(validationResponses::add))
                        .toList();
        CompletableFuture.allOf(productValidationFutures
                .toArray(CompletableFuture[]::new)).join();

        return validationResponses;
    }

    @Override
    public ProductResponse add(AddProductRequest request) {
        Product product = ProductMapper.mapToProductRequest(request);
        productRepository.save(product);
        return ProductMapper.mapToProductResponse(product);
    }

    @Override
    public Boolean remove(String productId) {
        productRepository.delete(productId);
        return true;
    }

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream().map(product -> ProductResponse.builder()
                .productId(product.getId())
                .url(product.getUrl())
                .build()).toList();
    }

    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "validationCallFallback")
    private ValidateProfileUpdateResponse invokeValidateCall(String url,
                                                             BusinessProfile profileForUpdate) {
        return restTemplate.postForObject(url, profileForUpdate,
                ValidateProfileUpdateResponse.class);
    }

    private ValidateProfileUpdateResponse validationCallFallback(Throwable throwable) {
        return ValidateProfileUpdateResponse.builder()
                .productId("unknown")
                .revisionStatus(RevisionStatus.UNAVAILABLE)
                .message("unknown")
                .build();
    }
}
