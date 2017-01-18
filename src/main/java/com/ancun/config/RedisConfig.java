package com.ancun.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

/**
 * Redis配置类
 *
 * @author jarvan [NO.0051]
 * @since 2017年01月18日 11:39
 */
@Configuration
public class RedisConfig {

    @Bean
    public StringRedisTemplate redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate(redisConnectionFactory);

        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    /**
     * Redis缓存管理 bean
     * @param redisTemplate
     * @return
     */
    @Bean
    @Primary
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // 使用自定义key名
        cacheManager.setUsePrefix(true);
        // Sets the default expire time (in seconds). 设置失效时间
        cacheManager.setDefaultExpiration(60 * 15 - 1);
        return cacheManager;
    }
}
