package com.splendor.notes.design.patterns.strategy.combination;

import lombok.extern.log4j.Log4j2;

/**
 * @author splendor.s
 * @create 2022/9/21 下午9:30
 * @description pc端微信支付，用户为未开通状态
 */
@Log4j2
@OrderHandlerType(source = OrderSourceEnum.PC, payMethod = OrderPayMethodEnum.WEIXIN, memberType = MemberTypeEnum.NONACTIVATED)
public class PCWXNonactivatedOrderHandler implements OrderHandler {
    /**
     * 进行原价支付
     *
     * @param order 基本订单情况
     * @return 实际消费结果
     */
    @Override
    public OrderConsumerResult handle(Order order) {
        return OrderConsumerResult.builder()
                .code(order.getCode())
                .amountSpent(order.getAmount())
                .right("该会员不参与其他权益履约适宜").build();
    }
}