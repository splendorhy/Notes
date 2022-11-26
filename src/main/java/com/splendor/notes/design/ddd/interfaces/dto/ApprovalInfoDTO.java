package com.splendor.notes.design.ddd.interfaces.dto;

import lombok.Data;

/**
 * @author splendor.s
 * @create 2022/11/25 下午6:56
 * @description
 */
@Data
public class ApprovalInfoDTO {

    String approvalInfoId;
    ApproverDTO approverDTO;
    String msg;
    long time;
}
