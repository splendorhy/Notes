package com.splendor.notes.design.patterns.responsibility.base;

import com.splendor.notes.design.patterns.template.check.CheckResponse;
import org.springframework.stereotype.Component;

/**
 * @author splendor.s
 * @create 2022/9/14 下午9:43
 * @description 风控校验
 */
@Component
public class ActRiskCheck implements ActivityCheck{

    /**
     * 活动风控检查
     *
     * @param activityDto 活动信息
     * @return 活动时效性检查结果 true-满足条件
     */
    @Override
    public CheckResponse check(ActivityDto activityDto) {
        /*风控校验1：如果当前活动非上线状态(1)，则不进行风控管理*/
        if (activityDto.getStatus() != 1) {
            return CheckResponse.builder().pass(true).build();
        }
        /*风控校验2：相关描述不得包含敏感词(以下用于模拟)*/
        String sensitive = "有敏感词";
        if (activityDto.getActivityName().contains(sensitive) || activityDto.getActivityConfig().contains(sensitive)) {
            return CheckResponse.builder().pass(false).errorMsg("[活动设置中存在敏感词，请修正相关配置内容！]").build();
        }

        return CheckResponse.builder().pass(true).build();
    }
}
