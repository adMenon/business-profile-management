package com.intuitcraft.businessprofilemanagement.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.intuitcraft.businessprofilemanagement.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.intuitcraft.businessprofilemanagement.Util.getProductDummy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductRepositoryTest {

    private final DynamoDBMapper dynamoDBMapper = mock(DynamoDBMapper.class);
    private ProductRepository repository;

    @BeforeEach
    void setup() {
        repository = new ProductRepository(dynamoDBMapper);
    }

    @Test
    void save() {
        doNothing().when(dynamoDBMapper).save(any(Product.class));
        Product product = repository.save(getProductDummy());
        assertNotNull(product);
        verify(dynamoDBMapper, times(1)).save(any(Product.class));
    }


    @Test
    void findById() {
        when(dynamoDBMapper.load(any(Class.class), anyString())).thenReturn(getProductDummy());
        Product product = repository.findById("product_id");
        assertNotNull(product);
        verify(dynamoDBMapper, times(1)).load(any(Class.class), anyString());
    }

    @Test
    void update() {
        doNothing().when(dynamoDBMapper).save(any(Product.class), any(DynamoDBSaveExpression.class));
        String productResult = repository.update("profile_id", getProductDummy());
        assertNotNull(productResult);
        verify(dynamoDBMapper, times(1)).save(any(Product.class), any(DynamoDBSaveExpression.class));

    }
}