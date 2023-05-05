package com.splendor.notes.design.patterns.responsibility.pipeline.common;

import com.splendor.notes.design.patterns.responsibility.pipeline.ContextHandler;
import com.splendor.notes.design.patterns.responsibility.pipeline.model.PipelineContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author splendor.s
 * @create 2023/4/18 下午6:20
 * @description
 */
@Component
public class CommonTailHandler implements ContextHandler<PipelineContext, Object> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object handle(PipelineContext context) {
        logger.info("管道执行完毕：管道名称为【{}】, context={}", context.getName(), context);
        /*设置处理结束时间*/
        context.setEndTime(LocalDateTime.now());
        return null;
    }
}
