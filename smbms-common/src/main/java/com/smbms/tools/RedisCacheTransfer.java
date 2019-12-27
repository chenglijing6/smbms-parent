package com.smbms.tools;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 构造一个RedisCacheTransfer的注入
 */
public class RedisCacheTransfer {
    /*public void setRedisTemplate(RedisTemplate redisTemplate) {

        RedisCache.setRedisTemplate(redisTemplate);
    }*/

    public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        RedisCache.setJedisConnectionFactory(jedisConnectionFactory);
    }
}
