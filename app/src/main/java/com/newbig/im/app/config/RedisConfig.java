package com.newbig.im.app.config;

import com.newbig.im.common.serializer.FastJsonJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


//@Configuration
public class RedisConfig {

    /**
     * 序列化器
     *
     * @return
     */
    @Bean(name = "fastJsonJsonRedisSerializer")
    public RedisSerializer getFastJsonJsonRedisSerializer() {
        return new FastJsonJsonRedisSerializer<>(Object.class);
    }

    /**
     * 操作模板设置
     * getRedisTemplate
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, String> getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate template = new RedisTemplate();

        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
//        template.setValueSerializer(getFastJsonJsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(getFastJsonJsonRedisSerializer());
        template.afterPropertiesSet();

        return template;
    }


}
