package com.splendor.notes.design.patterns.decorator.commoditypricestrategy.util;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author splendor.s
 * @create 2022/11/28 下午3:49
 * @description 优惠券
 */
@Data
public class UserCoupon {
    /**
     * 优惠券ID
     */
    private int id;
    /**
     * 领取优惠券用户ID
     */
    private int userId;
    /**
     * 商品SKU
     */
    private String sku;
    /**
     * 优惠金额
     */
    private BigDecimal coupon;
}
