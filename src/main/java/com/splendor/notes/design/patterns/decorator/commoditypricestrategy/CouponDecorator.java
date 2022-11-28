package com.splendor.notes.design.patterns.decorator.commoditypricestrategy;

import com.splendor.notes.design.patterns.decorator.commoditypricestrategy.util.OrderDetail;
import com.splendor.notes.design.patterns.decorator.commoditypricestrategy.util.PromotionType;

import java.math.BigDecimal;

/**
 * @author splendor.s
 * @create 2022/11/28 下午3:47
 * @description 计算使用优惠券后的金额
 */
public class CouponDecorator extends BaseCountDecorator {
    public CouponDecorator(IBaseCount count) {
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
        BigDecimal coupon = orderDetail.getMerchandise().getSupportPromotions().get(PromotionType.COUPON).getUserCoupon().getCoupon();
        System.out.println("优惠券金额：" + coupon);
        orderDetail.setPayMoney(orderDetail.getPayMoney().subtract(coupon));
        return orderDetail.getPayMoney();
    }
}
