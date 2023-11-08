package com.intuitcraft.businessprofilemanagement.service.impl;

import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.intuitcraft.businessprofilemanagement.cache.Cache;
import com.intuitcraft.businessprofilemanagement.cache.Locker;
import com.intuitcraft.businessprofilemanagement.entities.BusinessProfile;
import com.intuitcraft.businessprofilemanagement.enums.RevisionStatus;
import com.intuitcraft.businessprofilemanagement.exceptions.service.RevisionRejectedException;
import com.intuitcraft.businessprofilemanagement.models.BusinessProfileResponse;
import com.intuitcraft.businessprofilemanagement.models.CreateBusinessProfileRequest;
import com.intuitcraft.businessprofilemanagement.models.ValidateProfileUpdateResponse;
import com.intuitcraft.businessprofilemanagement.repository.BusinessProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.intuitcraft.businessprofilemanagement.Util.getCreateBusinessProfileRequest;
import static com.intuitcraft.businessprofilemanagement.Util.getDummyBusinessProfile;
import static com.intuitcraft.businessprofilemanagement.Util.getUpdateBusinessProfileRequest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BusinessProfileServiceImplTest {

    private BusinessProfileServiceImpl businessProfileService;

    @Mock
    private BusinessProfileRepository businessProfileRepository;
    @Mock
    private ProductServiceImpl productService;
    @Mock
    private Cache cache;
    @Mock
    private Locker locker;

    @BeforeEach
    void init() {
        businessProfileService = new BusinessProfileServiceImpl(businessProfileRepository,
                productService, cache, locker);
    }

    @Test
    void create() {
        CreateBusinessProfileRequest request = getCreateBusinessProfileRequest();
        when(businessProfileRepository.save(any(BusinessProfile.class))).thenReturn(new BusinessProfile());
        businessProfileService.create(request);
        verify(businessProfileRepository, times(1)).save(any(BusinessProfile.class));

    }

    @Test
    void findById_getFromCache() {
        when(cache.get(anyString(), any(Class.class))).thenReturn(Optional.of(getDummyBusinessProfile()));
        BusinessProfileResponse response = businessProfileService.findById("profile_id");
        assertNotNull(response);
        verify(cache, times(1)).get(anyString(), any(Class.class));
    }

    @Test
    void findById_getFromDb() {
        when(cache.get(anyString(), any(Class.class))).thenReturn(Optional.of(getDummyBusinessProfile()));
        when(businessProfileRepository.findById(anyString())).thenReturn(new BusinessProfile());
        BusinessProfileResponse response = businessProfileService.findById("profile_id");
        assertNotNull(response);
        verify(cache, times(1)).get(anyString(), any(Class.class));
        verify(businessProfileRepository, times(1)).findById(anyString());
    }

    @Test
    void update() {
        when(locker.hasLock(anyString(), anyLong(), any(TimeUnit.class))).thenReturn(true);
        when(cache.get(anyString(), any(Class.class))).thenReturn(Optional.of(getDummyBusinessProfile()));
        doNothing().when(cache).put(anyString(), any(BusinessProfile.class), anyLong(), any(TimeUnit.class));
        doNothing().when(locker).releaseLock(anyString());
        when(productService.validateProfileUpdate(any(BusinessProfile.class), anySet())).thenReturn(List.of(ValidateProfileUpdateResponse.builder()
                .message("message")
                .revisionStatus(RevisionStatus.ACCEPTED)
                .productId("product_id")
                .build()));
        when(businessProfileRepository.update(anyString(), any())).thenReturn("profile_id");
        Boolean result = businessProfileService.update("profile_id", getUpdateBusinessProfileRequest());
        assertNotNull(result);
        verify(cache, times(1)).get(anyString(), any(Class.class));
        verify(locker, times(1)).hasLock(anyString(), anyLong(), any(TimeUnit.class));
        verify(cache, times(1)).put(anyString(), any(BusinessProfile.class), anyLong(), any(TimeUnit.class));
        verify(locker, times(1)).releaseLock(anyString());
        verify(businessProfileRepository, times(1)).update(anyString(), any());
        verify(productService, times(1)).validateProfileUpdate(any(BusinessProfile.class), anySet());
    }

    @Test
    void updateThrowRevisionRejectedException() {
        when(locker.hasLock(anyString(), anyLong(), any(TimeUnit.class))).thenReturn(true);
        when(cache.get(anyString(), any(Class.class))).thenReturn(Optional.of(getDummyBusinessProfile()));
        when(productService.validateProfileUpdate(any(BusinessProfile.class), anySet())).thenReturn(List.of(ValidateProfileUpdateResponse.builder()
                .message("message")
                .revisionStatus(RevisionStatus.REJECTED)
                .productId("product_id")
                .build()));
        assertThrows(RevisionRejectedException.class, () -> businessProfileService.update("profile_id", getUpdateBusinessProfileRequest()));
        verify(cache, times(1)).get(anyString(), any(Class.class));
        verify(locker, times(1)).hasLock(anyString(), anyLong(), any(TimeUnit.class));
        verify(productService, times(1)).validateProfileUpdate(any(BusinessProfile.class), anySet());
    }

    @Test
    void updateThrowResourceInUseException() {
        when(locker.hasLock(anyString(), anyLong(), any(TimeUnit.class))).thenReturn(false);
        assertThrows(ResourceInUseException.class, () -> businessProfileService.update("profile_id", getUpdateBusinessProfileRequest()));
        verify(locker, times(1)).hasLock(anyString(), anyLong(), any(TimeUnit.class));
    }

    @Test
    void subscribe() {
        when(locker.hasLock(anyString(), anyLong(), any(TimeUnit.class))).thenReturn(true);
        when(cache.get(anyString(), any(Class.class))).thenReturn(Optional.of(getDummyBusinessProfile()));
        doNothing().when(cache).put(anyString(), any(BusinessProfile.class), anyLong(), any(TimeUnit.class));
        doNothing().when(locker).releaseLock(anyString());
        when(businessProfileRepository.update(anyString(), any(BusinessProfile.class))).thenReturn("profile_id");
        Boolean result = businessProfileService.subscribe("profile_id", "product_id");
        assertNotNull(result);
        verify(cache, times(1)).get(anyString(), any(Class.class));
        verify(locker, times(1)).hasLock(anyString(), anyLong(), any(TimeUnit.class));
        verify(cache, times(1)).put(anyString(), any(BusinessProfile.class), anyLong(), any(TimeUnit.class));
        verify(locker, times(1)).releaseLock(anyString());
        verify(businessProfileRepository, times(1)).update(anyString(), any(BusinessProfile.class));

    }

    @Test
    void unsubscribe() {
        when(locker.hasLock(anyString(), anyLong(), any(TimeUnit.class))).thenReturn(true);
        when(cache.get(anyString(), any(Class.class))).thenReturn(Optional.of(getDummyBusinessProfile()));
        doNothing().when(cache).put(anyString(), any(BusinessProfile.class), anyLong(), any(TimeUnit.class));
        doNothing().when(locker).releaseLock(anyString());
        when(businessProfileRepository.update(anyString(), any(BusinessProfile.class))).thenReturn("profile_id");
        Boolean result = businessProfileService.unsubscribe("profile_id", "product_id");
        assertNotNull(result);
        verify(cache, times(1)).get(anyString(), any(Class.class));
        verify(locker, times(1)).hasLock(anyString(), anyLong(), any(TimeUnit.class));
        verify(cache, times(1)).put(anyString(), any(BusinessProfile.class), anyLong(), any(TimeUnit.class));
        verify(locker, times(1)).releaseLock(anyString());
        verify(businessProfileRepository, times(1)).update(anyString(), any(BusinessProfile.class));


    }
}