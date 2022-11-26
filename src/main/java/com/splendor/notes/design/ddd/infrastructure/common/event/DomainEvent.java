package com.splendor.notes.design.ddd.infrastructure.common.event;

import lombok.Data;

import java.util.Date;

/**
 * @author splendor.s
 * @create 2022/11/25 下午5:54
 * @description
 */
@Data
public class DomainEvent {

    String id;
    Date timestamp;
    String source;
    String data;
}