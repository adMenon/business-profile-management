package com.intuitcraft.businessprofilemanagement.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.intuitcraft.businessprofilemanagement.model.BusinessProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<BusinessProfile> findAll() {
        return dynamoDBMapper.scan(BusinessProfile.class, new DynamoDBScanExpression());
    }

    public String update(String id, BusinessProfile profile) {
        dynamoDBMapper.save(profile,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("profile_id",
                                new ExpectedAttributeValue(
                                        new AttributeValue().withS(id)
                                )));
        return id;
    }

    public void delete(String id) {
        BusinessProfile profile = dynamoDBMapper.load(BusinessProfile.class, id);
        dynamoDBMapper.delete(profile);
    }
}
