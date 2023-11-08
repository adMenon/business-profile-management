package com.intuitcraft.businessprofilemanagement.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.intuitcraft.businessprofilemanagement.entities.BusinessProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.intuitcraft.businessprofilemanagement.constants.Constants.BUSINESS_PROFILE_PK;

@Repository
@RequiredArgsConstructor
public class BusinessProfileRepository {
    private final DynamoDBMapper dynamoDBMapper;

    public BusinessProfile save(BusinessProfile profile) {
        dynamoDBMapper.save(profile);
        return profile;
    }

    public BusinessProfile findById(String id) {
        return dynamoDBMapper.load(BusinessProfile.class, id);
    }

    public String update(String id, BusinessProfile profile) {
        dynamoDBMapper.save(profile,
                new DynamoDBSaveExpression()
                        .withExpectedEntry(BUSINESS_PROFILE_PK,
                                new ExpectedAttributeValue(
                                        new AttributeValue().withS(id)
                                )));
        return id;
    }
}
