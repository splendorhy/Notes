package com.splendor.notes.design.ddd.interfaces.assembler;

import com.splendor.notes.design.ddd.domain.leave.entity.valueobject.Applicant;
import com.splendor.notes.design.ddd.interfaces.dto.ApplicantDTO;

/**
 * @author splendor.s
 * @create 2022/11/25 下午6:57
 * @description
 */
public class ApplicantAssembler {

    public static ApplicantDTO toDTO(Applicant applicant){
        ApplicantDTO dto = new ApplicantDTO();
        dto.setPersonId(applicant.getPersonId());
        dto.setPersonName(applicant.getPersonName());
        return dto;
    }

    public static Applicant toDO(ApplicantDTO dto){
        Applicant applicant = new Applicant();
        applicant.setPersonId(dto.getPersonId());
        applicant.setPersonName(dto.getPersonName());
        return applicant;
    }

}
