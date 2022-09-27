package com.splendor.notes.infrastructure.cache;

import lombok.Getter;

/**
 * @author splendor.s
 * @create 2022/7/6 下午4:09
 */
@Getter
public enum CacheType {
    //存取
    FULL,
    //只存
    PUT,
    //删除
    DELETE,;
}
