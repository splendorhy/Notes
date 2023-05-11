package com.splendor.notes.design.patterns.combination.engine.service.engine.impl;

import com.splendor.notes.design.patterns.combination.engine.model.EngineResult;
import com.splendor.notes.design.patterns.combination.engine.model.TreeNode;
import com.splendor.notes.design.patterns.combination.engine.model.TreeRich;
import com.splendor.notes.design.patterns.combination.engine.service.engine.EngineBase;

import java.util.Map;

/**
 * @author splendor.s
 * @create 2023/5/5 下午6:04
 * @description 决策引擎处理器定义
 */
public class TreeEngineHandle extends EngineBase {

    @Override
    public EngineResult process(Long treeId, String userId, TreeRich treeRich, Map<String, String> decisionMatter) {
        // 决策流程
        TreeNode treeNode = engineDecisionMaker(treeRich, treeId, userId, decisionMatter);
        // 决策结果
        return new EngineResult(userId, treeId, treeNode.getTreeNodeId(), treeNode.getNodeValue());
    }
}

