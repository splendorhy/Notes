package com.splendor.notes.design.ddd.domain.rule.repository.mapper;

import com.splendor.notes.design.ddd.domain.rule.entity.ApprovalRule;
import com.splendor.notes.design.ddd.domain.rule.repository.po.ApprovalRulePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author splendor.s
 * @create 2022/11/28 上午10:05
 * @description
 */

public interface ApprovalRuleDao extends JpaRepository<ApprovalRulePO, String> {

    @Query(value = "select r from ApprovalRulePO r where r.applicantRoleId=?1 and r.leaveType=?2 and duration=?3")
    ApprovalRule findRule(String applicantRoleId, String leaveType, long duration);
}
