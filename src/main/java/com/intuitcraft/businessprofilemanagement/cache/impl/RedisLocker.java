package com.intuitcraft.businessprofilemanagement.cache.impl;

import com.intuitcraft.businessprofilemanagement.cache.Cache;
import com.intuitcraft.businessprofilemanagement.cache.Locker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisLocker implements Locker {
    private final Cache cache;

    @Override
    public boolean hasLock(String key) {
        RedisAtomicInteger lockInt = cache.atomicInteger(key);
        return lockInt.incrementAndGet() == 1;
    }

    @Override
    public boolean hasLock(String key, long ttl, TimeUnit timeUnit) {
        RedisAtomicInteger lockInt = cache.atomicInteger(key, ttl, timeUnit);
        return lockInt.incrementAndGet() == 1;
    }

    @Override
    public void releaseLock(String key) {
        cache.delete(key);
    }
}
