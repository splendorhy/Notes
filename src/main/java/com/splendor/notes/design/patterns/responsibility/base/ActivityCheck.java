package com.splendor.notes.design.patterns.responsibility.base;

import com.splendor.notes.design.patterns.template.check.CheckResponse;

/**
 * @author splendor.s
 * @create 2022/9/14 下午9:41
 * 活动常规校验
 */
public interface ActivityCheck {

    /**
     * 活动常规校验
     *
     * @param activityDto 活动信息
     * @return 校验结果
     */
    CheckResponse check(ActivityDto activityDto);

}
