package com.splendor.notes.design.ddd.domain.rule.entity;

import com.splendor.notes.design.ddd.domain.leave.entity.Leave;
import lombok.Data;

/**
 * @author splendor.s
 * @create 2022/11/25 下午6:06
 * @description
 */
@Data
public class ApprovalRule {

    String personType;
    String leaveType;
    long duration;
    int maxLeaderLevel;

    public static ApprovalRule getByLeave(Leave leave){
        ApprovalRule rule = new ApprovalRule();
        rule.setPersonType(leave.getApplicant().getPersonType());
        rule.setLeaveType(leave.getType().toString());
        rule.setDuration(leave.getDuration());
        return rule;
    }
}
