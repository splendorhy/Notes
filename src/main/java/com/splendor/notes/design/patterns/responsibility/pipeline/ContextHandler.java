package com.splendor.notes.design.patterns.responsibility.pipeline;

/**
 * @author splendor.s
 * @create 2022/11/28 下午2:24
 * @description 管道中的上下文处理器  T：管道中的上下文 R：管道中的上下文处理结果
 */
public interface ContextHandler<T extends PipelineContext, R> {

    /**
     * 处理输入的上下文数据
     *
     * @param context  处理时的上下文数据:增加字段deliver为true则表示由下一个ContextHandler继续处理；为false则表示处理结束Content information
     * @param nextDeal 下一步处理内容：增加字段deliver为true则表示由下一个ContextHandler继续处理；为false则表示处理结束Content information
     */
    void handle(T context, R nextDeal);
}
