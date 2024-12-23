package com.example.teaDelivery.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RedisService {
    private final RedisTemplate<String,Object> template;

    public RedisService(RedisTemplate<String, Object> template) {
        this.template = template;
    }

    public void save(String key, Map<String, Long> basketItems){
        ValueOperations<String,Object> ops = template.opsForValue();
        ops.set(key,basketItems);
    }
    public Map<String, Long> get(String key){
        ValueOperations<String,Object> ops = template.opsForValue();
        return (Map<String, Long>) ops.get(key);
    }

    public void remove(String key){
        template.delete(key);
    }
}
