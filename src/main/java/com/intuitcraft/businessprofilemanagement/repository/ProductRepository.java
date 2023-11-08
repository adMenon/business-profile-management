package com.intuitcraft.businessprofilemanagement.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.intuitcraft.businessprofilemanagement.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {
    private final DynamoDBMapper dynamoDBMapper;

    public Product save(Product product) {
        dynamoDBMapper.save(product);
        return product;
    }

    public Product findById(String id) {
        return dynamoDBMapper.load(Product.class, id);
    }

    public List<Product> findAll() {
        return dynamoDBMapper.scan(Product.class, new DynamoDBScanExpression());
    }

    public String update(String id, Product product) {
        dynamoDBMapper.save(product,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("product_id",
                                new ExpectedAttributeValue(
                                        new AttributeValue().withS(id)
                                )));
        return id;
    }

    public void delete(String id) {
        Product product = dynamoDBMapper.load(Product.class, id);
        dynamoDBMapper.delete(product);
    }
}
