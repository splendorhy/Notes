package com.splendor.notes.design.patterns.decorator.commoditypricestrategy.util;

import lombok.Data;

/**
 * @author splendor.s
 * @create 2022/11/28 下午3:48
 * @description 促销类型
 */
@Data
public class SupportPromotions implements Cloneable {
    /**
     * 该商品促销的ID
     */
    private int id;
    /**
     * 促销类型 1\优惠券 2\红包
     */
    private PromotionType promotionType;
    /**
     * 优先级
     */
    private int priority;
    /**
     * 用户领取该商品的优惠券
     */
    private UserCoupon userCoupon;
    /**
     * 用户领取该商品的红包
     */
    private UserRedPacket userRedPacket;

    /**
     * 重写clone方法
     */
    @Override
    public SupportPromotions clone() {
        SupportPromotions supportPromotions = null;
        try {
            supportPromotions = (SupportPromotions) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return supportPromotions;
    }
}
