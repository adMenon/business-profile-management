package com.intuitcraft.businessprofilemanagement.service.impl;

import com.intuitcraft.businessprofilemanagement.cache.Cache;
import com.intuitcraft.businessprofilemanagement.dto.BusinessProfileResponse;
import com.intuitcraft.businessprofilemanagement.dto.CreateBusinessProfileRequest;
import com.intuitcraft.businessprofilemanagement.dto.UpdateBusinessProfileRequest;
import com.intuitcraft.businessprofilemanagement.dto.ValidateProfileUpdateResponse;
import com.intuitcraft.businessprofilemanagement.enums.RevisionStatus;
import com.intuitcraft.businessprofilemanagement.exceptions.service.RevisionRejectedException;
import com.intuitcraft.businessprofilemanagement.mappers.BusinessProfileMapper;
import com.intuitcraft.businessprofilemanagement.model.BusinessProfile;
import com.intuitcraft.businessprofilemanagement.repository.BusinessProfileRepository;
import com.intuitcraft.businessprofilemanagement.service.BusinessProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.intuitcraft.businessprofilemanagement.constants.Constants.BUSINESS_PROFILE;

@Service
@RequiredArgsConstructor
public class BusinessProfileServiceImpl implements BusinessProfileService {
    private final BusinessProfileRepository businessProfileRepository;
    private final ProductServiceImpl productService;
    private final Cache cache;

    @Override
    public BusinessProfileResponse create(CreateBusinessProfileRequest request) {
        BusinessProfile profile = BusinessProfileMapper.mapToBusinessProfile(request);
        businessProfileRepository.save(profile);
        addToCache(profile);
        return BusinessProfileMapper.mapToBusinessProfileResponse(profile);
    }

    @Override
    public BusinessProfileResponse findById(String id) {
        BusinessProfile profile = getFromCache(id)
                .orElse(businessProfileRepository.findById(id));
        return BusinessProfileMapper.mapToBusinessProfileResponse(profile);
    }

    @Override
    public BusinessProfileResponse update(String id, UpdateBusinessProfileRequest request) {
        BusinessProfile profile = getFromCache(id)
                .orElse(businessProfileRepository.findById(id));
        Set<String> subscribedProducts = profile.getSubscribedProducts();
        BusinessProfile profileForUpdate = BusinessProfileMapper.mapToBusinessProfile(profile, request);
        validateUpdate(profileForUpdate, subscribedProducts);
        businessProfileRepository.update(id, profileForUpdate);
        addToCache(profileForUpdate);
        return BusinessProfileMapper.mapToBusinessProfileResponse(profile);

    }

    @Override
    public Boolean subscribe(String id, String productId) {
        BusinessProfile profile = businessProfileRepository.findById(id);
        profile.subscribe(productId);
        businessProfileRepository.update(id, profile);
        return true;
    }

    @Override
    public Boolean unsubscribe(String id, String productId) {
        BusinessProfile profile = businessProfileRepository.findById(id);
        profile.unsubscribe(productId);
        businessProfileRepository.update(id, profile);
        return true;
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

    private void deleteFromCache(String profileId) {
        String cacheKey = BUSINESS_PROFILE + "_" + profileId;
        cache.delete(cacheKey);
    }
}
