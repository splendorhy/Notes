package com.splendor.notes.design.patterns.decorator.commoditypricestrategy.util;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;
/**
 * @author splendor.s
 * @create 2022/11/28 下午3:48
 * @description 商品实体
 */
@Data
public class Merchandise {
    /**
     * 商品SKU
     */
    private String sku;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品单价
     */
    private BigDecimal price;
    /**
     * 支持促销类型
     */
    private Map<PromotionType, SupportPromotions> supportPromotions;
}