package com.splendor.notes.design.ddd.domain.person.entity;

import lombok.Data;

/**
 * @author splendor.s
 * @create 2022/11/25 下午6:04
 * @description
 */
@Data
public class Relationship {

    String id;
    String personId;
    String leaderId;
    int leaderLevel;
}
