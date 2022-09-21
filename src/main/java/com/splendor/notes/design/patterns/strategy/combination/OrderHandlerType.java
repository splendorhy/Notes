package com.splendor.notes.design.patterns.strategy.combination;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author splendor.s
 * @create 2022/9/21 下午9:23
 * @description 订单处理类型
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface OrderHandlerType {
    /**
     * 指明来源
     */
    OrderSourceEnum source();

    /**
     * 支付方式
     */
    OrderPayMethodEnum payMethod();

    /**
     * 会员类型
     */
    MemberTypeEnum memberType();
}
