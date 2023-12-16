package com.employment.config;

import com.employment.constant.RedisConstant;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.Random;

/**
 * @program: StudentEmploymentSystem
 * @ClassName CacheConfig
 * @author: c9noo
 * @create: 2023-11-16 14:10
 * @Version 1.0
 * redis缓存设置
 **/
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    Random random = new Random();

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(getRandomizedTtl()))
                .build();
        return cacheManager;
    }

    /**
     * 获取随机化的过期时间
     * @return
     */
    private Duration getRandomizedTtl() {
        int randomSeconds = random.nextInt(RedisConstant.RANDOM_TIME);// 生成0到60之间的随机数
        return Duration.ofSeconds(RedisConstant.REDIS_TIME + randomSeconds);  // 基础过期时间为10800 秒，加上随机秒数
    }

}
