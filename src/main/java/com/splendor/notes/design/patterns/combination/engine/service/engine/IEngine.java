package com.splendor.notes.design.patterns.combination.engine.service.engine;

import com.splendor.notes.design.patterns.combination.engine.model.EngineResult;
import com.splendor.notes.design.patterns.combination.engine.model.TreeRich;

import java.util.Map;

/**
 * @author splendor.s
 * @create 2023/5/5 下午6:01
 * @description 决策引擎流程接口定义
 *
 *   组合模式不只是可以运用于规则决策树，
 *   还可以做服务包装将不同的接口进行组合配置，
 *   对外提供服务能力，减少开发成本
 */
public interface IEngine {

    EngineResult process(final Long treeId, final String userId, TreeRich treeRich,
                         final Map<String, String> decisionMatter);

}
