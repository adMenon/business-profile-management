package com.intuitcraft.businessprofilemanagement.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
public class Address {
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String country;
    private String zip;

    @Override
    public String toString() {
        return line1 + ", " + line2 + ", " + city + ", " + state + ", " + country + "-" + zip;
    }

}
