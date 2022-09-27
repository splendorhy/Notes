package com.splendor.notes.infrastructure.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


/**
 * @author splendor.s
 * @create 2022/7/6 下午4:03
 */

@Configuration
public class CaffeineConfig {

    @Bean
    public Cache<String,Object> caffeineCache(){
        return Caffeine.newBuilder()
                //初始大小
                .initialCapacity(128)
                //最大数量
                .maximumSize(1024)
                //过期时间
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .build();
    }

}


