package com.splendor.notes.design.ddd.domain.person.repository.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author splendor.s
 * @create 2022/11/28 上午9:56
 * @description
 */
@Data
@Entity
public class RelationshipPO {

    @Id
    String id;
    String personId;
    String leaderId;
}