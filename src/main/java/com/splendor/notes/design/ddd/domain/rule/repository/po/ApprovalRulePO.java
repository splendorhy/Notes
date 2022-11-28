package com.splendor.notes.design.ddd.domain.rule.repository.po;

import com.splendor.notes.design.ddd.domain.leave.entity.valueobject.LeaveType;
import com.splendor.notes.design.ddd.domain.person.entity.valueobject.PersonType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author splendor.s
 * @create 2022/11/28 上午10:06
 * @description
 */
@Entity
@Data
public class ApprovalRulePO {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    String id;

    @Enumerated(EnumType.STRING)
    LeaveType leaveType;

    @Enumerated(EnumType.STRING)
    PersonType personType;

    long duration;
    String applicantRoleId;
}
