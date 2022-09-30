package com.splendor.notes.thread.reactor;

import lombok.*;

import java.io.Serializable;

/**
 * @author splendor.s 测试实体
 * @create 2022/9/27 下午3:06
 */
@Data
@Builder
@ToString(callSuper = false)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class HasResourceInfo implements Serializable {

    /**
     * 通知内容
     */
    private String content;

    /**
     * 通知主题
     */
    private String title;

    /**
     * 提醒时间
     */
    private String remindTime;

    /**
     * 通知类型
     */
    private Integer notifyType;

    /**
     * 医院ID
     */
    private Long doctorId;

    /**
     * 医生名称
     */
    private String doctorName;

    /**
     * 科室ID
     */
    private Long depId;

    /**
     * 医院ID
     */
    private Long unitId;

}
