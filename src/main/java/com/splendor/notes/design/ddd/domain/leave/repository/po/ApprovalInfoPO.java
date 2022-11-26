package com.splendor.notes.design.ddd.domain.leave.repository.po;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author splendor.s
 * @create 2022/11/25 下午6:18
 * @description
 */
@Entity
@Data
public class ApprovalInfoPO {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    String approvalInfoId;
    String leaveId;
    String applicantId;
    String approverId;
    int approverLevel;
    String approverName;
    String msg;
    long time;

}
