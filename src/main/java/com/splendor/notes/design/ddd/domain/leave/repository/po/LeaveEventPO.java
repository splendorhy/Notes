package com.splendor.notes.design.ddd.domain.leave.repository.po;

import com.splendor.notes.design.ddd.domain.leave.event.LeaveEventType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
/**
 * @author splendor.s
 * @create 2022/11/25 下午6:18
 * @description
 */

@Data
@Entity
public class LeaveEventPO {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator = "idGenerator")
    int id;
    @Enumerated(EnumType.STRING)
    LeaveEventType leaveEventType;
    Date timestamp;
    String source;
    String data;
}
