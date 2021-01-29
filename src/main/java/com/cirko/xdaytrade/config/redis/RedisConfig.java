package com.cirko.xdaytrade.config.redis;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {
    /**
     * 重写 Redis 序列化方式，使用 Json 方式:
     * RedisTemplate 默认使用的是 JdkSerializationRedisSerializer，
     * 在此将自己配置 RedisTemplate 并定义使用 jackson 提供的 Serializer。
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置键（key）的序列化采用StringRedisSerializer。
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 设置值（Value）的序列化采用fastJson序列化
        redisTemplate.setValueSerializer(new GenericFastJsonRedisSerializer());

        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        GenericFastJsonRedisSerializer genericFastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        RedisCacheConfiguration config = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericFastJsonRedisSerializer))
                .disableCachingNullValues();

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }

    class DataJedisProperties {
        @Value("${spring.redis.host}")
        private String host;
        @Value("${spring.redis.password}")
        private String password;
        @Value("${spring.redis.port}")
        private int port;
        @Value("${spring.redis.timeout}")
        private int timeout;
        @Value("${spring.redis.jedis.pool.max-idle}")
        private int maxIdle;
        @Value("${spring.redis.jedis.pool.max-wait}")
        private long maxWaitMillis;

        @Bean
        JedisConnectionFactory jedisConnectionFactory() {
            JedisConnectionFactory factory = new JedisConnectionFactory();
            factory.setHostName(host);
            factory.setPort(port);
            factory.setTimeout(timeout);
            factory.setPassword(password);
            return factory;
        }
//        @Bean
//        public JedisPool redisPoolFactory() {
//            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//            jedisPoolConfig.setMaxIdle(maxIdle);
//            jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//
//            JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
//            return jedisPool;
//        }
    }
}
