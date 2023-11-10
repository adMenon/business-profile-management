package com.intuitcraft.businessprofilemanagement.cache.impl;

import com.intuitcraft.businessprofilemanagement.cache.Cache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;

class RedisLockerTest {

    @Mock
    private Cache cache;

    @InjectMocks
    private RedisLocker redisLocker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void hasLock_shouldReturnTrue_whenLockIsAcquired() {
        // Arrange
        String key = "testKey";
        RedisAtomicInteger lockInt = Mockito.mock(RedisAtomicInteger.class);

        Mockito.when(cache.atomicInteger(eq(key))).thenReturn(lockInt);
        Mockito.when(lockInt.incrementAndGet()).thenReturn(1);

        // Act
        boolean result = redisLocker.hasLock(key);

        // Assert
        assertTrue(result);
    }

    @Test
    void hasLock_shouldReturnFalse_whenLockIsNotAcquired() {
        // Arrange
        String key = "testKey";
        RedisAtomicInteger lockInt = Mockito.mock(RedisAtomicInteger.class);

        Mockito.when(cache.atomicInteger(eq(key))).thenReturn(lockInt);
        Mockito.when(lockInt.incrementAndGet()).thenReturn(2);

        // Act
        boolean result = redisLocker.hasLock(key);

        // Assert
        assertFalse(result);
    }

    @Test
    void hasLockWithTtl_shouldReturnTrue_whenLockIsAcquired() {
        // Arrange
        String key = "testKey";
        RedisAtomicInteger lockInt = Mockito.mock(RedisAtomicInteger.class);

        Mockito.when(cache.atomicInteger(eq(key), anyLong(), any(TimeUnit.class))).thenReturn(lockInt);
        Mockito.when(lockInt.incrementAndGet()).thenReturn(1);

        // Act
        boolean result = redisLocker.hasLock(key, 10, TimeUnit.SECONDS);

        // Assert
        assertTrue(result);
    }

    @Test
    void hasLockWithTtl_shouldReturnFalse_whenLockIsNotAcquired() {
        // Arrange
        String key = "testKey";
        RedisAtomicInteger lockInt = Mockito.mock(RedisAtomicInteger.class);

        Mockito.when(cache.atomicInteger(eq(key), anyLong(), any(TimeUnit.class))).thenReturn(lockInt);
        Mockito.when(lockInt.incrementAndGet()).thenReturn(2);

        // Act
        boolean result = redisLocker.hasLock(key, 10, TimeUnit.SECONDS);

        // Assert
        assertFalse(result);
    }

    @Test
    void releaseLock_shouldDeleteKeyFromCache() {
        // Arrange
        String key = "testKey";

        // Act
        redisLocker.releaseLock(key);

        // Assert
        Mockito.verify(cache).delete(eq(key));
    }
}