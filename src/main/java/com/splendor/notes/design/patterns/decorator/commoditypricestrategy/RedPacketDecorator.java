package com.splendor.notes.design.patterns.decorator.commoditypricestrategy;

import com.splendor.notes.design.patterns.decorator.commoditypricestrategy.util.OrderDetail;
import com.splendor.notes.design.patterns.decorator.commoditypricestrategy.util.PromotionType;

import java.math.BigDecimal;

/**
 * @author splendor.s
 * @create 2022/11/28 下午3:47
 * @description 计算使用红包后的金额
 */
public class RedPacketDecorator extends BaseCountDecorator {

    public RedPacketDecorator(IBaseCount count) {
        super(count);
    }

    @Override
    public BigDecimal countPayMoney(OrderDetail orderDetail) {
        BigDecimal payTotalMoney = new BigDecimal(0);
        payTotalMoney = super.countPayMoney(orderDetail);
        payTotalMoney = countCouponPayMoney(orderDetail);
        return payTotalMoney;
    }

    private BigDecimal countCouponPayMoney(OrderDetail orderDetail) {

        BigDecimal redPacket = orderDetail.getMerchandise().getSupportPromotions().get(PromotionType.REDPACKED).getUserRedPacket().getRedPacket();
        System.out.println("红包优惠金额：" + redPacket);

        orderDetail.setPayMoney(orderDetail.getPayMoney().subtract(redPacket));
        return orderDetail.getPayMoney();
    }
}
