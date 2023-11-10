package com.intuitcraft.businessprofilemanagement.cache.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuitcraft.businessprofilemanagement.exceptions.fatal.CacheOperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class RedisCacheImplTest {

    private final ValueOperations<String, String> valueOperations = mock(ValueOperations.class);
    @Mock
    private RedisTemplate<String, String> redisTemplate;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private RedisCacheImpl redisCache;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void put_shouldSetInCache_whenValidKeyAndValue() throws Exception {
        // Arrange
        String key = "testKey";
        Object yourObject = new Object();
        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.doNothing().when(valueOperations).set(anyString(), anyString());
        Mockito.when(objectMapper.writeValueAsString(any())).thenReturn("serializedValue");

        // Act
        redisCache.put(key, yourObject);

        // Assert
        Mockito.verify(redisTemplate, times(1)).opsForValue();
        Mockito.verify(valueOperations, times(1)).set(anyString(), anyString());
    }

    @Test
    void put_shouldThrowCacheOperationException_whenSerializationFails() throws Exception {
        // Arrange
        String key = "testKey";
        Object yourObject = new Object();

        Mockito.when(objectMapper.writeValueAsString(any())).thenThrow(new RuntimeException("Serialization error"));

        // Act & Assert
        assertThrows(CacheOperationException.class, () -> redisCache.put(key, yourObject));
    }

    @Test
    void putWithTtl_shouldSetInCacheWithTTL_whenValidKeyAndValue() throws Exception {
        // Arrange
        String key = "testKey";
        Object yourObject = new Object();

        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.doNothing().when(valueOperations).set(anyString(), anyString());
        Mockito.when(objectMapper.writeValueAsString(any())).thenReturn("serializedValue");

        // Act
        redisCache.put(key, yourObject, 10, TimeUnit.SECONDS);

        // Assert
        Mockito.verify(redisTemplate, times(1)).opsForValue();
        Mockito.verify(valueOperations, times(1)).set(anyString(), anyString());
        Mockito.verify(redisTemplate).expire(eq(key), eq(10L), eq(TimeUnit.SECONDS));
    }

    @Test
    void get_shouldReturnOptionalWithValue_whenValidKeyAndType() throws Exception {
        // Arrange
        String key = "testKey";
        Object yourObject = new Object();
        String serializedValue = "serializedValue";
        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.when(valueOperations.get(eq(key))).thenReturn(serializedValue);
        Mockito.when(objectMapper.readValue(eq(serializedValue), any(Class.class))).thenReturn(yourObject);

        // Act
        Optional<Object> result = redisCache.get(key, Object.class);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(yourObject, result.get());
    }

    @Test
    void get_shouldReturnEmptyOptional_whenKeyNotFound() {
        // Arrange
        String key = "nonexistentKey";
        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.when(valueOperations.get(eq(key))).thenReturn(null);

        // Act
        Optional<Object> result = redisCache.get(key, Object.class);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void get_shouldThrowCacheOperationException_whenSerializationFails() throws Exception {
        // Arrange
        String key = "testKey";
        String serializedValue = "invalidSerializedValue";

        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.when(valueOperations.get(eq(key))).thenReturn(serializedValue);
        Mockito.when(objectMapper.readValue(eq(serializedValue), any(Class.class))).thenThrow(new RuntimeException("Deserialization error"));

        // Act & Assert
        assertThrows(CacheOperationException.class, () -> redisCache.get(key, Object.class));
    }

    @Test
    void getWithTypeReference_shouldReturnOptionalWithValue_whenValidKeyAndTypeReference() throws Exception {
        // Arrange
        String key = "testKey";
        Object yourObject = new Object();
        String serializedValue = "serializedValue";

        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.when(valueOperations.get(eq(key))).thenReturn(serializedValue);
        Mockito.when(objectMapper.readValue(eq(serializedValue), any(TypeReference.class))).thenReturn(yourObject);

        // Act
        Optional<Object> result = redisCache.get(key, new TypeReference<Object>() {
        });

        // Assert
        assertTrue(result.isPresent());
        assertEquals(yourObject, result.get());
    }

    @Test
    void getWithTypeReference_shouldReturnEmptyOptional_whenKeyNotFound() {
        // Arrange
        String key = "nonexistentKey";

        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.when(valueOperations.get(eq(key))).thenReturn(null);

        // Act
        Optional<Object> result = redisCache.get(key, new TypeReference<Object>() {
        });

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getWithTypeReference_shouldThrowCacheOperationException_whenSerializationFails() throws Exception {
        // Arrange
        String key = "testKey";
        String serializedValue = "invalidSerializedValue";

        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.when(valueOperations.get(eq(key))).thenReturn(serializedValue);
        Mockito.when(objectMapper.readValue(eq(serializedValue), any(TypeReference.class))).thenThrow(new RuntimeException("Deserialization error"));

        // Act & Assert
        assertThrows(CacheOperationException.class, () -> redisCache.get(key, new TypeReference<Object>() {
        }));
    }

    @Test
    void delete_shouldExpireKey_whenValidKey() {
        // Arrange
        String key = "testKey";
        Mockito.when(redisTemplate.getConnectionFactory()).thenReturn(mock(RedisConnectionFactory.class));
        // Act
        redisCache.delete(key);

        // Assert
        Mockito.verify(redisTemplate).expire(eq(key), eq(0L), eq(TimeUnit.MICROSECONDS));
    }

    @Test
    void atomicInteger_shouldReturnNewInstance() {
        // Arrange
        String key = "testKey";
        RedisConnectionFactory factory = mock(RedisConnectionFactory.class);
        RedisConnection connection = mock(RedisConnection.class);

        when(redisTemplate.getConnectionFactory()).thenReturn(factory);
        when(factory.getConnection()).thenReturn(connection);
        // Act
        RedisAtomicInteger result = redisCache.atomicInteger(key);

        // Assert
        assertNotNull(result);
        assertEquals(key, result.getKey());
    }

    @Test
    void atomicIntegerWithTtl_shouldReturnNewInstanceWithTTL() {
        // Arrange
        String key = "testKey";
        RedisConnectionFactory factory = mock(RedisConnectionFactory.class);
        RedisConnection connection = mock(RedisConnection.class);

        when(redisTemplate.getConnectionFactory()).thenReturn(factory);
        when(factory.getConnection()).thenReturn(connection);

        // Act
        RedisAtomicInteger result = redisCache.atomicInteger(key, 10, TimeUnit.SECONDS);

        // Assert
        assertNotNull(result);
        assertEquals(key, result.getKey());
    }
}
