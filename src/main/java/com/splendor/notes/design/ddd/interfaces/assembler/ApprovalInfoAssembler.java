package com.splendor.notes.design.ddd.interfaces.assembler;

import com.splendor.notes.design.ddd.domain.leave.entity.ApprovalInfo;
import com.splendor.notes.design.ddd.interfaces.dto.ApprovalInfoDTO;

/**
 * @author splendor.s
 * @create 2022/11/25 下午6:57
 * @description
 */
public class ApprovalInfoAssembler {

    public static ApprovalInfo toDO(ApprovalInfoDTO dto){
        ApprovalInfo approvalInfo = new ApprovalInfo();
        approvalInfo.setApprovalInfoId(dto.getApprovalInfoId());
        approvalInfo.setMsg(dto.getMsg());
        approvalInfo.setApprover(ApproverAssembler.toDO(dto.getApproverDTO()));
        return approvalInfo;
    }

    public static ApprovalInfoDTO toDTO(ApprovalInfo approvalInfo){
        ApprovalInfoDTO dto = new ApprovalInfoDTO();
        dto.setApprovalInfoId(approvalInfo.getApprovalInfoId());
        dto.setMsg(approvalInfo.getMsg());
        dto.setTime(approvalInfo.getTime());
        return dto;
    }

}
