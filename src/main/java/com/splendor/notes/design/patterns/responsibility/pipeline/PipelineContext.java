package com.splendor.notes.design.patterns.responsibility.pipeline;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author splendor.s
 * @create 2022/11/28 下午2:29
 * @description 管道的上下文基本结构
 */
@Getter
@Setter
public class PipelineContext {
    /**
     * 处理开始时间
     */
    private LocalDateTime startTime;
    /**
     * 处理结束时间
     */
    private LocalDateTime endTime;

    /**
     * 获取数据名称
     */
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
