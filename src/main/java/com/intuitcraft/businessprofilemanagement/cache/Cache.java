package com.intuitcraft.businessprofilemanagement.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.NonNull;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface Cache {

    <T> void put(@NonNull String key, T value);

    <T> void put(@NonNull String key, T value, long ttl, @NonNull TimeUnit timeUnit);

    <T> Optional<T> get(@NonNull String key, @NonNull TypeReference<T> type);

    <T> Optional<T> get(@NonNull String key, @NonNull Class<T> type);

    void delete(String key);

    RedisAtomicInteger atomicInteger(String key);

    RedisAtomicInteger atomicInteger(String key, long ttl, TimeUnit timeUnit);
}
