package com.splendor.notes.design.patterns.responsibility.pipeline;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


/**
 * @author splendor.s
 * @create 2022/11/28 上午10:25
 * @description
 */
@Data
@Builder
public class WordRegular {
    /**
     * 主键id
     */
    private long id;
    /**
     * 正则配置类型
     */
    private int type;
    /**
     * 敏感词词条
     */
    @NotNull
    @Length(max = 2000)
    private String words;
}
