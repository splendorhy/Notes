package com.splendor.notes.design.patterns.responsibility.pipeline.enums;

import java.lang.annotation.*;

/**
 * @author splendor.s
 * @create 2023/4/18 下午6:02
 * @description
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommonBiz {
    /**
     * 业务描述
     */
    int bizDesc();

    /**
     * 业务编码,具体业务链流程功能
     */
    int bizCode();

}
