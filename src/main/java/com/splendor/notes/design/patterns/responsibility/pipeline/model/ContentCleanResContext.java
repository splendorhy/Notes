package com.splendor.notes.design.patterns.responsibility.pipeline.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author splendor.s
 * @create 2023/4/18 下午6:33
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class ContentCleanResContext extends PipelineContext {
    /**
     * 清洗完成 true-清洗完成
     * 清洗阶段数据节点流转信号
     */
    private boolean isCleanDone;
    /**
     * 用户输入文本原稿(原始文本不变)
     */
    private String content;
    /**
     * 用户清洗后的文本内容
     */
    private String cleanContent;
    /**
     * 用户文本属性
     */
    private ContentAttr contentAttr;
    /**
     * 数据节点流转终止原因,只有isCleanDone为false生效
     */
    private String reason;

    @Override
    public String getName() {
        return "数据清洗结果构建上下文";
    }
}
