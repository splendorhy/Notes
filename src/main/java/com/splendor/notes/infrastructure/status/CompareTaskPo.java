package com.splendor.notes.infrastructure.status;

import lombok.Data;

/**
 * @author splendor.s
 * @create 2022/11/29 下午1:37
 * @description
 */
@Data
public class CompareTaskPo {

    private Integer status;

    private Integer version;

    private Integer id;

    private Integer lastPingTime;

    private String compareResult;

    private String noiseResult;

    private  String compareTaskName;

    private Integer replayTaskId;
}
