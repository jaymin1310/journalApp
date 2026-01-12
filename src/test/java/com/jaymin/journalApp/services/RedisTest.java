package com.jaymin.journalApp.services;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Disabled
    @Test
    public void test() {
        redisTemplate.opsForValue().set("email", "jvs@gmail.com");
        Object email=redisTemplate.opsForValue().get("email");
        int a=1;
    }

}
