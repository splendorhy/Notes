package com.splendor.notes.design.patterns.responsibility.chain;

import lombok.Builder;
import lombok.Data;

/**
 * @author splendor.s
 * @create 2022/9/19 下午5:55
 * @description 架构映射战略设计方案
 */
@Data
@Builder
public class ArchitectureMappingScheme {

    /**
     * 系统上下文
     */
    private String systemContext;
    /**
     * 业务架构 ： 业务组件 + 业务架构视图
     */
    private String businessrchitecture;
    /**
     * 应用架构 : 业务组件 + 应用架构视图
     */
    private String applicationArchitecture;
    /**
     * 子领域架构 ： 核心域、支撑域、通用域
     */
    private String subFieldAnalysis;

}
