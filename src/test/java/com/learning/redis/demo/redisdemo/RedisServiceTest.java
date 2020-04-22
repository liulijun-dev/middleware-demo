package com.learning.redis.demo.redisdemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SpringBootTest
public class RedisServiceTest {
    @Autowired
    private RedisService redisService;

    @Test
    public void should_get_right_value_after_setting_a_value() {
        redisService.setValue("cache_name", "redis");

        String cachedValue = redisService.getValue("cache_name").toString();

        Assertions.assertEquals("redis", cachedValue);
    }

    @Test
    public void should_not_get_value_when_it_has_timeout() throws InterruptedException {
        redisService.setTimeoutValue("cache_name", "redis", Duration.ofSeconds(2));

        Thread.sleep(2000);
        Object cachedValue = redisService.getValue("cache_name");

        Assertions.assertNull(cachedValue);
    }

    @Test
    public void should_increment_int_value_by_one_success() throws InterruptedException {
        redisService.setValue("cache_value", "1");

        redisService.increment("cache_value");
        int cachedValue = Integer.valueOf(redisService.getValue("cache_value").toString());

        Assertions.assertEquals(2, cachedValue);
    }

    @Test
    public void should_save_value_to_set_success() throws InterruptedException {
        redisService.setSet("cache_set", "1","2","3");

        Set cachedValue = redisService.getSet("cache_set");

        Assertions.assertTrue(cachedValue.contains("1"));
        Assertions.assertTrue(cachedValue.contains("2"));
        Assertions.assertTrue(cachedValue.contains("3"));
    }

    @Test
    public void should_remove_value_from_set_success() throws InterruptedException {
        redisService.setSet("cache_set", "1","2","3");

        redisService.removeSet("cache_set", "2");
        Set cachedValue = redisService.getSet("cache_set");

        Assertions.assertTrue(cachedValue.contains("1"));
        Assertions.assertFalse(cachedValue.contains("2"));
        Assertions.assertTrue(cachedValue.contains("3"));
    }

    @Test
    public void should_save_value_to_map_success() throws InterruptedException {
        Map<String, Object> map = new HashMap<>();
        map.put("name","Jimmy");
        map.put("job", "IT");
        redisService.setMap("cache_map", map);

        Map cachedValue = redisService.getMap("cache_map");

        Assertions.assertEquals("Jimmy", cachedValue.get("name"));
        Assertions.assertEquals("IT", cachedValue.get("job"));
    }

}
