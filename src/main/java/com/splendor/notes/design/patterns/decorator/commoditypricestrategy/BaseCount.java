package com.splendor.notes.design.patterns.decorator.commoditypricestrategy;

import com.splendor.notes.design.patterns.decorator.commoditypricestrategy.util.OrderDetail;

import java.math.BigDecimal;

/**
 * @author splendor.s
 * @create 2022/11/28 下午3:46
 * @description 支付基本类
 */
public class BaseCount implements IBaseCount {
    @Override
    public BigDecimal countPayMoney(OrderDetail orderDetail) {
        orderDetail.setPayMoney(orderDetail.getMerchandise().getPrice());
        System.out.println("商品原单价金额为：" + orderDetail.getPayMoney());
        return orderDetail.getPayMoney();
    }
}
