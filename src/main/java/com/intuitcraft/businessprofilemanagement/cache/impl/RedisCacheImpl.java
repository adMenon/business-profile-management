package com.intuitcraft.businessprofilemanagement.cache.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuitcraft.businessprofilemanagement.cache.Cache;
import com.intuitcraft.businessprofilemanagement.exceptions.fatal.CacheOperationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisCacheImpl implements Cache {

    private final RedisTemplate<String, String> redisTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public <T> void put(@NonNull String key, T value) {
        try {
            redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value));
        } catch (Exception e) {
            throw new CacheOperationException("Unable to put value in cache", e, key);
        }
    }

    @Override
    public <T> void put(@NonNull String key, T value, long ttl, @NonNull TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value));
            redisTemplate.expire(key, ttl, timeUnit);
        } catch (Exception e) {
            throw new CacheOperationException("Unable to put value in cache", e, key);
        }
    }

    @Override
    public <T> Optional<T> get(@NonNull String key, @NonNull Class<T> type) {
        try {
            return Optional.ofNullable(redisTemplate.opsForValue().get(key))
                    .map(valueAsJson -> {
                        try {
                            return objectMapper.readValue(valueAsJson, type);
                        } catch (JsonProcessingException e) {
                            throw new CacheOperationException("Unable to parse " + valueAsJson + " to " + type.getName(), e, key);
                        }
                    });
        } catch (Exception e) {
            throw new CacheOperationException("Unable to get value", e, key);
        }
    }

    @Override
    public <T> Optional<T> get(@NonNull String key, @NonNull TypeReference<T> type) {
        try {
            return Optional.ofNullable(redisTemplate.opsForValue().get(key))
                    .map(valueAsJson -> {
                        try {
                            return objectMapper.readValue(valueAsJson, type);
                        } catch (JsonProcessingException e) {
                            throw new CacheOperationException("Unable to parse " + valueAsJson + " to " + type.getType().getTypeName(), e, key);
                        }
                    });
        } catch (Exception e) {
            throw new CacheOperationException("Unable to get value", e, key);
        }
    }

    @Override
    public void delete(String key) {
        try {
            redisTemplate.expire(key, 0, TimeUnit.MICROSECONDS);
        } catch (Exception e) {
            throw new CacheOperationException("Unable to delete value", e, key);
        }
    }

    @Override
    public RedisAtomicInteger atomicInteger(String key) {
        return new RedisAtomicInteger(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
    }

    @Override
    public RedisAtomicInteger atomicInteger(String key, long ttl, TimeUnit timeUnit) {
        RedisAtomicInteger atomicInteger = new RedisAtomicInteger(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        atomicInteger.expire(ttl, timeUnit);
        return atomicInteger;
    }


}
