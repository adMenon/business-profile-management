package com.intuitcraft.businessprofilemanagement.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.intuitcraft.businessprofilemanagement.Util;
import com.intuitcraft.businessprofilemanagement.entities.BusinessProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BusinessProfileRepositoryTest {

    private final DynamoDBMapper dynamoDBMapper = mock(DynamoDBMapper.class);
    private BusinessProfileRepository repository;

    @BeforeEach
    void setup() {
        repository = new BusinessProfileRepository(dynamoDBMapper);
    }

    @Test
    void save() {
        doNothing().when(dynamoDBMapper).save(any(BusinessProfile.class));
        BusinessProfile profileResult = repository.save(Util.getDummyBusinessProfile());
        assertNotNull(profileResult);
        verify(dynamoDBMapper, times(1)).save(any(BusinessProfile.class));
    }

    @Test
    void findById() {
        when(dynamoDBMapper.load(any(Class.class), anyString())).thenReturn(Util.getDummyBusinessProfile());
        BusinessProfile profileResult = repository.findById("profile_id");
        assertNotNull(profileResult);
        verify(dynamoDBMapper, times(1)).load(any(Class.class), anyString());
    }

    @Test
    void update() {
        doNothing().when(dynamoDBMapper).save(any(BusinessProfile.class), any(DynamoDBSaveExpression.class));
        String profileResult = repository.update("profile_id", Util.getDummyBusinessProfile());
        assertNotNull(profileResult);
        verify(dynamoDBMapper, times(1)).save(any(BusinessProfile.class), any(DynamoDBSaveExpression.class));

    }

    @Test
    void delete() {
    }
}