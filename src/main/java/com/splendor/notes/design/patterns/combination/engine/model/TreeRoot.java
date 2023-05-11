package com.splendor.notes.design.patterns.combination.engine.model;

/**
 * @author splendor.s
 * @create 2023/5/5 下午6:12
 * @description 树根信息
 */
public class TreeRoot {
    /**
     * 规则树ID
     */
    private Long treeId;
    /**
     * 规则树名称
     */
    private String treeName;
    /**
     * 规则树根ID
     */
    private Long treeRootNodeId;

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public Long getTreeRootNodeId() {
        return treeRootNodeId;
    }

    public void setTreeRootNodeId(Long treeRootNodeId) {
        this.treeRootNodeId = treeRootNodeId;
    }
}
