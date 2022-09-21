package com.splendor.notes.design.patterns.strategy.complex;

import com.splendor.notes.design.patterns.strategy.combination.MemberTypeEnum;
import com.splendor.notes.design.patterns.strategy.combination.OrderPayMethodEnum;
import com.splendor.notes.design.patterns.strategy.combination.OrderSourceEnum;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author splendor.s
 * @create 2022/9/21 下午9:41
 * @description
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface OrderProcessorType {
    /**
     * 指明来源
     */
    OrderSourceEnum source();

    /**
     * 支付方式
     */
    OrderPayMethodEnum[] payMethod();

    /**
     * 会员类型
     */
    MemberTypeEnum memberType();
}
