package com.splendor.notes.design.patterns.strategy.complex;

import com.splendor.notes.design.patterns.strategy.combination.MemberTypeEnum;
import com.splendor.notes.design.patterns.strategy.combination.OrderPayMethodEnum;
import com.splendor.notes.design.patterns.strategy.combination.OrderSourceEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

/**
 * @author splendor.s
 * @create 2022/9/21 下午9:43
 * @description 策略的基本组合体
 */
@Data
@Builder
public class StrategyInfo {
    /**
     * 指明来源
     */
    OrderSourceEnum source;
    /**
     * 支付方式
     */
    OrderPayMethodEnum payMethod;
    /**
     * 会员类型
     */
    MemberTypeEnum memberType;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StrategyInfo)) {
            return false;
        }
        StrategyInfo that = (StrategyInfo) o;
        return source == that.source &&
                payMethod == that.payMethod &&
                memberType == that.memberType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, payMethod, memberType);
    }
}


