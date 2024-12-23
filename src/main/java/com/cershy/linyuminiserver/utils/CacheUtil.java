package com.cershy.linyuminiserver.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CacheUtil {
    //记录用户最后一次查询记录的用户Id
    private final Cache<String, String> userReadMsgCache;

    public CacheUtil() {
        this.userReadMsgCache = Caffeine.newBuilder()
                .expireAfterWrite(12, TimeUnit.HOURS)
                .build();
    }

    public void putUserReadCache(String userId, String targetId) {
        userReadMsgCache.put(userId, targetId);
    }

    public String getUserReadCache(String userId) {
        return userReadMsgCache.getIfPresent(userId);
    }
}
