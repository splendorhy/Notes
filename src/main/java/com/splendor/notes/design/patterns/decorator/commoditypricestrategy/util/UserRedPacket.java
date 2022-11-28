package com.splendor.notes.design.patterns.decorator.commoditypricestrategy.util;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author splendor.s
 * @create 2022/11/28 下午3:49
 * @description 红包
 */
@Data
public class UserRedPacket {
    /**
     * 红包ID
     */
    private int id;
    /**
     * 领取用户ID
     */
    private int userId;
    /**
     * 商品SKU
     */
    private String sku;
    /**
     * 领取红包金额
     */
    private BigDecimal redPacket;
}
