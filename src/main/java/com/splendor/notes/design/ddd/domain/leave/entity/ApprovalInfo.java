package com.splendor.notes.design.ddd.domain.leave.entity;

import com.splendor.notes.design.ddd.domain.leave.entity.valueobject.ApprovalType;
import com.splendor.notes.design.ddd.domain.leave.entity.valueobject.Approver;
import lombok.Data;

/**
 * @author splendor.s
 * @create 2022/11/25 下午6:02
 * @description
 */
@Data
public class ApprovalInfo {

    String approvalInfoId;
    Approver approver;
    ApprovalType approvalType;
    String msg;
    long time;

}