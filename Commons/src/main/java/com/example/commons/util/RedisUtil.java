package com.example.commons.util;

import com.example.commons.Vo.UserVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void SaveUser(StringRedisTemplate stringRedisTemplate, UserVo user, String token) throws JsonProcessingException {
        // 手动序列化
        String json = mapper.writeValueAsString(user);
        // 写入数据
        stringRedisTemplate.opsForValue().setIfAbsent(token, json,30, TimeUnit.MINUTES);

    }
    public static UserVo getUser(StringRedisTemplate stringRedisTemplate,String token) throws JsonProcessingException {

        // 获取数据
        String jsonUser = stringRedisTemplate.opsForValue().get(token);
        // 手动反序列化
        UserVo user = mapper.readValue(jsonUser, UserVo.class);
        System.out.println("user = " + user);
        return user;
    }
}
