package com.splendor.notes.design.patterns.responsibility.chain;

import lombok.Builder;
import lombok.Data;

/**
 * @author splendor.s
 * @create 2022/9/19 下午5:52
 * 领域驱动设计产物报告提交内容
 */
@Data
@Builder
public class DDDProductReport {

    /**
     * 全局分析规格说明书
     */
    private GlobalAnalysisSpec globalAnalysisSpec;
    /**
     * 架构映射战略设计方案
     */
    private ArchitectureMappingScheme architectureMappingScheme;
    /**
     * 领域模型构建产物
     */
    private DomainModelBuildProduct domainModelBuildProduct;


}
