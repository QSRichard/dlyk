package com.richard.manger;


import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class RedisManger {


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public Object getValue(String key) {

        return redisTemplate.opsForList().range(key, 0, -1);
    }


    public <T> Object setValue(String key, Collection<T> value) {


        Object[] arrayValue = new Object[value.size()];
        value.toArray(arrayValue);

        return redisTemplate.opsForList().leftPushAll(key, arrayValue);
    }
}
