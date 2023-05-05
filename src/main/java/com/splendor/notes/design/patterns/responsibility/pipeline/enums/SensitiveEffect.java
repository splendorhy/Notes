package com.splendor.notes.design.patterns.responsibility.pipeline.enums;

import java.lang.annotation.*;

/**
 * @author splendor.s
 * @create 2023/4/18 下午4:42
 * @description
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SensitiveEffect {
    /**
     * 生效编码
     */
    int effectCode();
}
