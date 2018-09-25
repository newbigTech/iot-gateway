package com.newbig.im.service;

import com.newbig.im.common.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    private static final String USER_HOST_KEY_PREFIX="user_host_hash_";
    private static final Integer NUM_OF_ONE_KEY=100_000;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public Object getUserHost(Long userId){
        return redisTemplate.opsForHash().get(getUserHostKey(userId),userId.toString());
    }
    public void addUserHost(Long userId,String host){
        redisTemplate.opsForHash().put(getUserHostKey(userId),userId.toString(),host);
    }
    public void deleteUserHost(Long userId){
        redisTemplate.opsForHash().delete(getUserHostKey(userId),userId.toString());
    }
    private String getUserHostKey(Long userId){
        return StringUtil.concat(USER_HOST_KEY_PREFIX,userId/NUM_OF_ONE_KEY);
    }
}
