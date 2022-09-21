package com.splendor.notes.design.patterns.responsibility.chain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author splendor.s
 * @create 2022/9/19 下午5:58
 * @description 领域驱动设计产物报告提交内容校验结果反馈
 */
@Data
@Builder
public class DDDReportValidateRes {

    /**
     * 报告是否符合产出 true-符合要求；false-不符合规范
     */
    private boolean legal;
    /**
     * 如果报告不符合要求，给出详细原因
     */
    private List<String> detailReasons;

}
