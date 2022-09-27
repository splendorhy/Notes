package com.splendor.notes.infrastructure.cache;

import java.lang.annotation.*;

/**
 * @author splendor.s
 * @create 2022/7/6 下午4:06
 * 本地缓存标识
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomCache {

    String cacheName();

    String key(); //支持springEl表达式

    long l2TimeOut() default 120;

    CacheType type() default CacheType.FULL;

}
