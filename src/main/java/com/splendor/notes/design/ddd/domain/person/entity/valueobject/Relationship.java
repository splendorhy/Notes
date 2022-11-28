package com.splendor.notes.design.ddd.domain.person.entity.valueobject;

import lombok.Data;

/**
 * @author splendor.s
 * @create 2022/11/28 上午9:53
 * @description
 */
@Data
public class Relationship {

    String id;
    String personId;
    String leaderId;
    int leaderLevel;
}
