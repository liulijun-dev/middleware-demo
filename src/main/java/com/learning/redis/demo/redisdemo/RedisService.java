package com.learning.redis.demo.redisdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.Set;

@Service
public class RedisService {

    private RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Object getValue(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setValue(final String key, final Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setTimeoutValue(final String key, final Object value, final Duration timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    public void increment(final String key) {
        redisTemplate.opsForValue().increment(key);
    }

    public void setSet(final String key, final Object... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    public Set<Object> getSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public void removeSet(final String key, final Object... values) {
        redisTemplate.opsForSet().remove(key, values);
    }

    public void setMap(final String key, final Map map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public Map getMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }
}
