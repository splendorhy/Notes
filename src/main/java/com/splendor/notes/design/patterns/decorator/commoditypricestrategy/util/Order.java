package com.splendor.notes.design.patterns.decorator.commoditypricestrategy.util;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
/**
 * @author splendor.s
 * @create 2022/11/28 下午3:48
 * @description 主订单
 */
@Data
public class Order {
    /**
     * 订单ID
     */
    private int id;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 总支付金额
     */
    private BigDecimal totalPayMoney;
    /**
     * 详细订单列表
     */
    private List<OrderDetail> list;
}
