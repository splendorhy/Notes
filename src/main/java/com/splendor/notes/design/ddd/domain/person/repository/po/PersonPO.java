package com.splendor.notes.design.ddd.domain.person.repository.po;

import com.splendor.notes.design.ddd.domain.person.entity.valueobject.PersonStatus;
import com.splendor.notes.design.ddd.domain.person.entity.valueobject.PersonType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author splendor.s
 * @create 2022/11/28 上午9:55
 * @description
 */
@Data
@Entity
@Table(name = "person")
public class PersonPO {

    @Id
    String personId;
    String personName;
    String departmentId;
    @Enumerated(EnumType.STRING)
    PersonType personType;
    @Transient
    String leaderId;
    int roleLevel;
    Date createTime;
    Date lastModifyTime;
    @Enumerated(EnumType.STRING)
    PersonStatus status;
    @OneToOne
    RelationshipPO relationshipPO;
}

