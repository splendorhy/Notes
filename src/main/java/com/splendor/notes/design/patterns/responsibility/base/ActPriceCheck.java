package com.splendor.notes.design.patterns.responsibility.base;

import com.splendor.notes.design.patterns.template.check.CheckResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author splendor.s
 * @create 2022/9/14 下午9:45
 * @description 价格管控
 */
@Component
public class ActPriceCheck implements ActivityCheck {

    /**
     * 活动价格检查
     * 1.降价活动;2.限时活动;3.买赠活动;4.首购优惠活动;5.自动续费活动;6.折上优惠活动
     *
     * @param activityDto 活动信息
     * @return 活动时效性检查结果 true-满足条件
     */
    @Override
    public CheckResponse check(ActivityDto activityDto) {
        Integer activityType = activityDto.getActivityType();
        BigDecimal activityPrice = activityDto.getActivityPrice();
        if (activityType.equals(1) && !(activityPrice.compareTo(BigDecimal.valueOf(10)) > -1 && activityPrice.compareTo(BigDecimal.valueOf(100)) < 1)) {
            return CheckResponse.builder().pass(false).errorMsg("[降价活动价格必须在10-100之间，请修正价格信息！]").build();
        }
        if (activityType.equals(2) && !(activityPrice.compareTo(BigDecimal.valueOf(10)) > -1 && activityPrice.compareTo(BigDecimal.valueOf(60)) < 1)) {
            return CheckResponse.builder().pass(false).errorMsg("[限时活动价格必须在10-60之间，请修正价格信息！]").build();
        }

        return CheckResponse.builder().pass(true).build();
    }

}
