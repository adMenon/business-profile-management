package com.intuitcraft.businessprofilemanagement.cache;

import java.util.concurrent.TimeUnit;

public interface Locker {

    boolean hasLock(String key);

    boolean hasLock(String key, long ttl, TimeUnit timeUnit);

    void releaseLock(String key);
}
