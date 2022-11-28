package com.splendor.notes.design.ddd.domain.rule.repository.facade;

import com.splendor.notes.design.ddd.domain.rule.entity.ApprovalRule;

/**
 * @author splendor.s
 * @create 2022/11/28 上午10:05
 * @description
 */
public interface ApprovalRuleRepositoryInterface {

    int getLeaderMaxLevel(ApprovalRule rule);
}
