package com.splendor.notes.design.patterns.decorator.commoditypricestrategy.util;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author splendor.s
 * @create 2022/11/28 下午3:48
 * @description 详细订单
 */
@Data
public class OrderDetail {
    /**
     * 详细订单ID
     */
    private int id;
    /**
     * 主订单ID
     */
    private int orderId;
    /**
     * 商品详情
     */
    private Merchandise merchandise;
    /**
     * 支付单价
     */
    private BigDecimal payMoney;
}
