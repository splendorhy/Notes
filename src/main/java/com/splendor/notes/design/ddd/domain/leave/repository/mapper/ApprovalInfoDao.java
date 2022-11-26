package com.splendor.notes.design.ddd.domain.leave.repository.mapper;

import com.splendor.notes.design.ddd.domain.leave.repository.po.ApprovalInfoPO;
import com.splendor.notes.design.ddd.domain.leave.repository.po.LeavePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author splendor.s
 * @create 2022/11/25 下午6:14
 * @description
 */
@Repository
public interface ApprovalInfoDao extends JpaRepository<ApprovalInfoPO, String> {

    List<LeavePO> queryByApplicantId(String applicantId);

    List<ApprovalInfoPO> queryByLeaveId(String leaveId);

}