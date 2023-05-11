package com.splendor.notes.design.patterns.combination.engine.model;

/**
 * @author splendor.s
 * @create 2023/5/5 下午6:09
 * @description
 */

import java.util.Map;

/**
        * Description: 规则树聚合对象
        * <br/>
        * TreeRich
        *
        * @author laiql
        * @date 2021/12/3 14:29
        */
public class TreeRich {

    /**
     * 树根信息
     */
    private TreeRoot treeRoot;
    /**
     * 树节点ID -> 子节点
     */
    private Map<Long, TreeNode> treeNodeMap;

    public TreeRich(TreeRoot treeRoot, Map<Long, TreeNode> treeNodeMap) {
        this.treeRoot = treeRoot;
        this.treeNodeMap = treeNodeMap;
    }

    public TreeRoot getTreeRoot() {
        return treeRoot;
    }

    public void setTreeRoot(TreeRoot treeRoot) {
        this.treeRoot = treeRoot;
    }

    public Map<Long, TreeNode> getTreeNodeMap() {
        return treeNodeMap;
    }

    public void setTreeNodeMap(Map<Long, TreeNode> treeNodeMap) {
        this.treeNodeMap = treeNodeMap;
    }
}
