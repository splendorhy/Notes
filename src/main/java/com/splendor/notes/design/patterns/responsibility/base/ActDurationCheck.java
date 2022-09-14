package com.splendor.notes.design.patterns.responsibility.base;

import com.splendor.notes.design.patterns.template.check.CheckResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author splendor.s
 * @create 2022/9/14 下午9:46
 * @description 时效性检验
 */
@Component
public class ActDurationCheck implements ActivityCheck {

    /**
     * 活动时效性检查
     *
     * @param activityDto 活动信息
     * @return 活动时效性检查结果 true-满足条件
     */
    @Override
    public CheckResponse check(ActivityDto activityDto) {
        /*检查时效性1：如果当前活动为上线状态(1)，则该活动时间必须在指定的时间范围内*/
        if (activityDto.getStatus() != 1) {
            return CheckResponse.builder().pass(true).build();
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime curTime = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.parse(activityDto.getStartTime(), dtf);
        LocalDateTime endTime = LocalDateTime.parse(activityDto.getEndTime(), dtf);
        if (startTime.isBefore(curTime) && endTime.isAfter(curTime)) {
            return CheckResponse.builder().pass(true).build();
        }

        return CheckResponse.builder().pass(false).errorMsg("[当前线上活动已失效，请检查修正状态！]").build();
    }

}
