package com.richard.service;


import java.util.concurrent.TimeUnit;

public interface RedisService {

    void setValue(String key, Object value);

    Object getValue(String key);

    boolean removeValue(String key);

    Boolean expire(String key, Long timeout, TimeUnit unit);
}
