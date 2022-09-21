package com.splendor.notes.design.patterns.strategy.combination;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author splendor.s
 * @create 2022/9/21 下午9:19
 * @description 订单实体
 */
@Data
public class Order {
    /**
     * 订单编号
     */
    private String code;
    /**
     * 订单金额
     */
    private BigDecimal amount;
    /**
     * 订单来源：手机端、pc端
     */
    private OrderSourceEnum source;
    /**
     * 支付方式:微信支付、支付宝支付、银行卡支付、本地钱包支付等
     */
    private OrderPayMethodEnum payMethod;
    /**
     * 订单支付会员归类：
     */
    private MemberTypeEnum payMember;
}
