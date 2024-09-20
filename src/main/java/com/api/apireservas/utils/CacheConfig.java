package com.api.apireservas.utils;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;


import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig())
                .transactionAware()
                .build();
    }

    private RedisCacheConfiguration defaultCacheConfig() {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(Settings.TTL_MINUTES));
    }

    @Bean
    public CacheManager cacheManagerNoTTL(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfigWithoutTTL())
                .transactionAware()
                .build();
    }

    private RedisCacheConfiguration defaultCacheConfigWithoutTTL() {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ZERO);
    }

    @Bean
    public CacheManager cacheManagerWithHoursTTL(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfigWithHoursTTL())
                .transactionAware()
                .build();
    }

    private RedisCacheConfiguration defaultCacheConfigWithHoursTTL() {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofHours(Settings.TTL_HOURS));
    }

    @Bean
    public CacheManager cacheManagerWithSecondsTTL(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfigWithSecondsTTL())
                .transactionAware()
                .build();
    }

    private RedisCacheConfiguration defaultCacheConfigWithSecondsTTL() {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(Settings.TTL_SECONDS));
    }
}
