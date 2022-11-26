package com.splendor.notes.design.ddd.interfaces.assembler;

import com.splendor.notes.design.ddd.domain.leave.entity.valueobject.Approver;
import com.splendor.notes.design.ddd.interfaces.dto.ApproverDTO;

/**
 * @author splendor.s
 * @create 2022/11/25 下午6:57
 * @description
 */
public class ApproverAssembler {

    public static ApproverDTO toDTO(Approver approver){
        ApproverDTO dto = new ApproverDTO();
        dto.setPersonId(approver.getPersonId());
        dto.setPersonName(approver.getPersonName());
        return dto;
    }

    public static Approver toDO(ApproverDTO dto){
        Approver approver = new Approver();
        approver.setPersonId(dto.getPersonId());
        approver.setPersonName(dto.getPersonName());
        return approver;
    }

}
