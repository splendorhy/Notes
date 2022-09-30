package com.splendor.notes.infrastructure.util.constant.redis;

/**
 * @author splendor.s
 * @create 2022/9/30 下午6:41
 * @description redis缓存时间
 */
public interface RedisTimePrefix {

    /**
     * 30秒
     */
    int EXPIRE_THREE_DECOND = 30;

    /**
     * 5分钟
     */
    int EXPIRE_FIVE_MINUTE = 300;

    /**
     * 15分钟
     */
    int EXPIRE_FIFTEEN_MINUTE = 900;

    /**
     * 30分钟
     */
    int EXPIRE_THIRTY_MINUTE = 1800;

    /**
     * 1小时
     */
    int EXPIRE_TIME = 3600;

    /**
     * 1天
     */
    int EXPIRE_ONE_DAY = 86400;

    /**
     * 账号类型缓存过期时间,7天
     */
    int EXPIRE_SEVEN_DAY = 604800;

}
