package com.intuitcraft.businessprofilemanagement.service.impl;

import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.amazonaws.util.CollectionUtils;
import com.intuitcraft.businessprofilemanagement.cache.Cache;
import com.intuitcraft.businessprofilemanagement.cache.Locker;
import com.intuitcraft.businessprofilemanagement.entities.BusinessProfile;
import com.intuitcraft.businessprofilemanagement.enums.RevisionStatus;
import com.intuitcraft.businessprofilemanagement.exceptions.service.RevisionRejectedException;
import com.intuitcraft.businessprofilemanagement.mappers.BusinessProfileMapper;
import com.intuitcraft.businessprofilemanagement.models.BusinessProfileResponse;
import com.intuitcraft.businessprofilemanagement.models.CreateBusinessProfileRequest;
import com.intuitcraft.businessprofilemanagement.models.UpdateBusinessProfileRequest;
import com.intuitcraft.businessprofilemanagement.models.ValidateProfileUpdateResponse;
import com.intuitcraft.businessprofilemanagement.repository.BusinessProfileRepository;
import com.intuitcraft.businessprofilemanagement.service.BusinessProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.intuitcraft.businessprofilemanagement.constants.Constants.BUSINESS_PROFILE;
import static com.intuitcraft.businessprofilemanagement.constants.Constants.PROFILE_LOCK;

@Service
@RequiredArgsConstructor
public class BusinessProfileServiceImpl implements BusinessProfileService {
    private final BusinessProfileRepository businessProfileRepository;
    private final ProductServiceImpl productService;
    private final Cache cache;
    private final Locker locker;


    @Override
    public String create(CreateBusinessProfileRequest request) {
        BusinessProfile profile = BusinessProfileMapper.mapToBusinessProfile(request);
        businessProfileRepository.save(profile);
        return profile.getId();
    }

    @Override
    public BusinessProfileResponse findById(String id) {
        BusinessProfile profile = getFromCache(id)
                .orElse(businessProfileRepository.findById(id));
        return BusinessProfileMapper.mapToBusinessProfileResponse(profile);
    }

    @Override
    @Retryable(value = {ResourceInUseException.class}, maxAttempts = 3)
    public Boolean update(String id, UpdateBusinessProfileRequest request) {
        String profileLock = PROFILE_LOCK + "_" + id;
        if (locker.hasLock(profileLock, 100,
                TimeUnit.MILLISECONDS)) {
            BusinessProfile profile = getFromCache(id)
                    .orElse(businessProfileRepository.findById(id));
            Set<String> subscribedProducts = profile.getSubscribedProducts();
            BusinessProfile profileForUpdate = BusinessProfileMapper.mapToBusinessProfile(profile, request);
            if (!CollectionUtils.isNullOrEmpty(subscribedProducts)) {
                validateUpdate(profileForUpdate, subscribedProducts);
            }
            businessProfileRepository.update(id, profileForUpdate);
            addToCache(profileForUpdate);
            locker.releaseLock(profileLock);
            return true;
        } else {
            throw getResourceInUseException(id);
        }
    }

    @Override
    public Boolean subscribe(String id, String productId) {
        String profileLock = PROFILE_LOCK + "_" + id;
        if (locker.hasLock(profileLock, 100,
                TimeUnit.MILLISECONDS)) {
            BusinessProfile profile = getFromCache(id)
                    .orElse(businessProfileRepository.findById(id));
            profile.subscribe(productId);
            businessProfileRepository.update(id, profile);
            addToCache(profile);
            locker.releaseLock(profileLock);
            return true;
        } else {
            throw getResourceInUseException(id);
        }
    }

    @Override
    public Boolean unsubscribe(String id, String productId) {
        String profileLock = PROFILE_LOCK + "_" + id;
        if (locker.hasLock(profileLock, 100,
                TimeUnit.MILLISECONDS)) {
            BusinessProfile profile = getFromCache(id)
                    .orElse(businessProfileRepository.findById(id));
            profile.unsubscribe(productId);
            businessProfileRepository.update(id, profile);
            addToCache(profile);
            locker.releaseLock(profileLock);
            return true;
        } else {
            throw getResourceInUseException(id);
        }
    }

    @Override
    public List<BusinessProfileResponse> findAll() {
        return businessProfileRepository.findAll().stream()
                .map(BusinessProfileMapper::mapToBusinessProfileResponse).collect(Collectors.toList());
    }

    private void validateUpdate(BusinessProfile profileForUpdate,
                                Set<String> subscribedProducts) {
        List<ValidateProfileUpdateResponse> validationResponses =
                productService.validateProfileUpdate(profileForUpdate, subscribedProducts)
                        .stream().filter(response -> !RevisionStatus.ACCEPTED
                                .equals(response.getRevisionStatus())).toList();
        if (!validationResponses.isEmpty()) {
            throw new RevisionRejectedException("Revision cannot be approved",
                    HttpStatus.FORBIDDEN, validationResponses.stream()
                    .map(ValidateProfileUpdateResponse::getRevisionMessage)
                    .toArray(String[]::new));
        }
    }

    private void addToCache(BusinessProfile profile) {
        String cacheKey = BUSINESS_PROFILE + "_" + profile.getId();
        cache.put(cacheKey, profile, 2, TimeUnit.MINUTES);
    }

    private Optional<BusinessProfile> getFromCache(String profileId) {
        String cacheKey = BUSINESS_PROFILE + "_" + profileId;
        return cache.get(cacheKey, BusinessProfile.class);
    }

    private ResourceInUseException getResourceInUseException(String id) {
        return new ResourceInUseException("Business Profile -" + id + "is currently in use");
    }
}
