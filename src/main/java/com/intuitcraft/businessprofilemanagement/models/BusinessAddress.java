package com.intuitcraft.businessprofilemanagement.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@DynamoDBDocument
public class BusinessAddress extends Address {
    public BusinessAddress(Address address) {
        super(address.getLine1(), address.getLine2(), address.getCity(),
                address.getState(), address.getCountry(), address.getZip());
    }
}
