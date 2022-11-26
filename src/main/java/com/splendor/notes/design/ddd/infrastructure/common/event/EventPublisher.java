package com.splendor.notes.design.ddd.infrastructure.common.event;

import com.splendor.notes.design.ddd.domain.leave.event.LeaveEvent;
import org.springframework.stereotype.Service;

/**
 * @author splendor.s
 * @create 2022/11/25 下午5:55
 * @description
 */
@Service
public class EventPublisher {

    public void publish(LeaveEvent event){
        //send to MQ
        //mq.send(event);
    }
}
