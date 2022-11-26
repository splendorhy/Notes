package com.splendor.notes.design.ddd.domain.leave.event;


import com.alibaba.fastjson.JSON;
import com.splendor.notes.design.ddd.domain.leave.entity.Leave;
import com.splendor.notes.design.ddd.infrastructure.common.event.DomainEvent;
import com.splendor.notes.design.ddd.infrastructure.util.IdGenerator;
import lombok.Data;

import java.util.Date;

/**
 * @author splendor.s
 * @create 2022/11/25 下午6:07
 * @description
 */
@Data
public class LeaveEvent extends DomainEvent {

    LeaveEventType leaveEventType;

    public static LeaveEvent create(LeaveEventType eventType, Leave leave){
        LeaveEvent event = new LeaveEvent();
        event.setId(IdGenerator.nextId());
        event.setLeaveEventType(eventType);
        event.setTimestamp(new Date());
        event.setData(JSON.toJSONString(leave));
        return event;
    }
}
