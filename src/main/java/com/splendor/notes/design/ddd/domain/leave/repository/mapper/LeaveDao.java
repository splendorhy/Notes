package com.splendor.notes.design.ddd.domain.leave.repository.mapper;


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
public interface LeaveDao extends JpaRepository<LeavePO, String> {

    List<LeavePO> queryByApplicantId(String applicantId);

    List<LeavePO> queryByApproverId(String approverId);
}
