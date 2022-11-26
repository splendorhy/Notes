package com.splendor.notes.design.ddd.domain.leave.repository.facade;

/**
 * @author splendor.s
 * @create 2022/11/25 下午6:13
 * @description
 */
import com.splendor.notes.design.ddd.domain.leave.repository.po.LeaveEventPO;
import com.splendor.notes.design.ddd.domain.leave.repository.po.LeavePO;

import java.util.List;

public interface LeaveRepositoryInterface {

    void save(LeavePO leavePO);

    void saveEvent(LeaveEventPO leaveEventPO);

    LeavePO findById(String id);

    List<LeavePO> queryByApplicantId(String applicantId);

    List<LeavePO> queryByApproverId(String approverId);

}
