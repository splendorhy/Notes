package com.splendor.notes.design.patterns.strategy.combination;

import lombok.extern.log4j.Log4j2;

/**
 * @author splendor.s
 * @create 2022/9/21 下午9:30
 * @description pc端本地钱包支付支付，用户为未开通状态
 */
@Log4j2
@OrderHandlerType(source = OrderSourceEnum.PC, payMethod = OrderPayMethodEnum.WALLET, memberType = MemberTypeEnum.NONACTIVATED)
public class PCWalletNonactivatedOrderHandler implements OrderHandler {
    /**
     * 存入钱包50得60，订单满30减30
     *
     * @param order 基本订单情况
     * @return 实际消费结果
     */
    @Override
    public OrderConsumerResult handle(Order order) {
        return OrderConsumerResult.builder()
                .code(order.getCode())
                .amountSpent(order.getAmount())
                .right("存入钱包50得60，订单满30减30").build();
    }
}
