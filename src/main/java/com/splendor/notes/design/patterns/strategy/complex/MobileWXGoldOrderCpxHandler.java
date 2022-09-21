package com.splendor.notes.design.patterns.strategy.complex;

import com.splendor.notes.design.patterns.strategy.combination.*;
import lombok.extern.log4j.Log4j2;

/**
 * @author splendor.s
 * @create 2022/9/21 下午9:46
 * @description 手机端微信支付，用户当前为黄金会员
 */
@Log4j2
@OrderProcessorType(source = OrderSourceEnum.MOBILE, payMethod = OrderPayMethodEnum.WEIXIN, memberType = MemberTypeEnum.GOLD)
public class MobileWXGoldOrderCpxHandler implements OrderHandler {
    /**
     * 套餐支持满160减140优惠、减110、减100各一张优惠，同时送特别礼品（随机）
     *
     * @param order 基本订单情况
     * @return 实际消费结果
     */
    @Override
    public OrderConsumerResult handle(Order order) {
        return OrderConsumerResult.builder()
                .code(order.getCode())
                .amountSpent(order.getAmount())
                .right("本次套餐支持满160减140优惠、减110、减100各一张优惠，同时送特别礼品（随机），后续履约").build();
    }
}

