package com.splendor.notes.design.patterns.combination.engine.service.logic;

import com.splendor.notes.design.patterns.combination.engine.model.TreeNodeLink;

import java.util.List;
import java.util.Map;

/**
 * @author splendor.s
 * @create 2023/5/5 下午6:05
 * @description 逻辑决策器接口
 */
public interface LogicFilter {


    /**
     * 逻辑决策器
     *
     * @param matterValue          决策值
     * @param treeNodeLineInfoList 决策节点
     * @return 下一个节点Id
     */
    Long filter(String matterValue, List<TreeNodeLink> treeNodeLineInfoList);

    /**
     * 获取决策值
     *
     * @param decisionMatter 决策物料
     * @return 决策值
     */
    String matterValue(Long treeId, String userId, Map<String, String> decisionMatter);
}
