package com.intuitcraft.businessprofilemanagement.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@DynamoDBTable(tableName = "product")
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
    @DynamoDBHashKey(attributeName = "product_id")
    private String id;

    @DynamoDBAttribute
    private String url;
}
