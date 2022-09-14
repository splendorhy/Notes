package com.splendor.notes.design.patterns.responsibility.base;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author splendor.s
 * @create 2022/9/14 下午9:41
 * @description 活动对应业务处理
 */
@Service
public class ActivityBizService {

    @Autowired
    private List<ActivityCheck> activityCheckList;

    /**
     * 活动常规校验结果
     *
     * @param activityDto 活动信息
     * @return z
     */
    public List<String> check(ActivityDto activityDto) {
        List<String> checkResults = Lists.newArrayList();
        for (ActivityCheck activityCheck : activityCheckList) {
            if (!activityCheck.check(activityDto).isPass()) {
                checkResults.add(activityCheck.check(activityDto).getErrorMsg());
            }
        }
        return checkResults;
    }
}

