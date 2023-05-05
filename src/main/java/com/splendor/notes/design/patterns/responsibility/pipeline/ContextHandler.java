package com.splendor.notes.design.patterns.responsibility.pipeline;

import com.splendor.notes.design.patterns.responsibility.pipeline.model.PipelineContext;

/**
 * @author splendor.s
 * @create 2022/11/28 下午2:24
 * @description 管道中的上下文处理器  T：管道中的上下文 R：管道中的上下文处理结果
 */
public interface ContextHandler<T extends PipelineContext, R> {
    /**
     * @param context 处理时的上下文数据
     * @return dealRes R 处理结果
     */
    R handle(T context);
}

