package com.splendor.notes.design.patterns.responsibility.pipeline;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author splendor.s
 * @create 2022/11/28 下午2:41
 * @description 最终生效的敏感词
 */
@Builder
@Data
public class SensitiveEffectRes {
    /**
     * 用户输入文本原稿
     */
    private String content;
    /**
     * 是否命中敏感词
     */
    private Boolean isHit;
    /**
     * 生效的敏感词
     */
    private List<SensitiveWord> hitWords;
}
