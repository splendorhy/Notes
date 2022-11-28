package com.splendor.notes.design.patterns.decorator.commoditypricestrategy;

import com.splendor.notes.design.patterns.decorator.commoditypricestrategy.util.OrderDetail;

import java.math.BigDecimal;

/**
 * @author splendor.s
 * @create 2022/11/28 下午3:47
 * @description 计算支付金额接口类
 */
public interface IBaseCount {
    /**
     * 功能描述：计算支付金额
     * @param orderDetail
     * @return BigDecimal
     */
    BigDecimal countPayMoney(OrderDetail orderDetail);
}
