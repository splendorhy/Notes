package com.splendor.notes.infrastructure.status;

import java.lang.annotation.*;

/**
 * @author splendor.s
 * @create 2022/11/29 上午11:24
 * @description 状态转换注解
 * 自动化根据状态进行统一管理，故各个处理器实际上需要表明自己需要处理的状态行为
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Status {
    int status();
}
