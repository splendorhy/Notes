package com.splendor.notes.design.ddd.interfaces.dto;

import lombok.Data;

import java.util.List;

/**
 * @author splendor.s
 * @create 2022/11/25 下午6:56
 * @description
 */
@Data
public class LeaveDTO {

    String leaveId;
    ApplicantDTO applicantDTO;
    ApproverDTO approverDTO;
    String leaveType;
    ApprovalInfoDTO currentApprovalInfoDTO;
    List<ApprovalInfoDTO> historyApprovalInfoDTOList;
    String startTime;
    String endTime;
    long duration;
    String status;

}
