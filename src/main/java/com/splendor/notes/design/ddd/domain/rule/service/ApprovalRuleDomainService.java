package com.splendor.notes.design.ddd.domain.rule.service;

import com.splendor.notes.design.ddd.domain.rule.entity.ApprovalRule;
import com.splendor.notes.design.ddd.domain.rule.repository.facade.ApprovalRuleRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author splendor.s
 * @create 2022/11/28 上午10:10
 * @description
 */
@Service
public class ApprovalRuleDomainService {

    @Autowired
    ApprovalRuleRepositoryInterface repositoryInterface;

    public int getLeaderMaxLevel(String personType, String leaveType, long duration) {
        ApprovalRule rule = new ApprovalRule();
        rule.setPersonType(personType);
        rule.setLeaveType(leaveType);
        rule.setDuration(duration);
        return repositoryInterface.getLeaderMaxLevel(rule);
    }
}
