package com.splendor.notes.design.patterns.responsibility.pipeline.enums;

import java.lang.annotation.*;

/**
 * @author splendor.s
 * @create 2023/4/18 下午4:37
 * @description
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SensitiveValidate {
    /**
     * 生效编码
     */
    int validateCode();

}
