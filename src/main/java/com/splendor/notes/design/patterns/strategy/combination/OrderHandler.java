package com.splendor.notes.design.patterns.strategy.combination;

/**
 * @author splendor.s
 * @create 2022/9/21 下午9:26
 * @description
 */
public interface OrderHandler {
    /**
     * 处理订单生成消费结果
     *
     * @param order 订单基本信息
     * @return 订单实际消费结果
     */
    OrderConsumerResult handle(Order order);
}