package com.splendor.notes.design.patterns.combination.base;

/**
 * @author splendor.s
 * @create 2023/5/5 下午5:58
 * @description 组合模式客户端测试定义：
 */
public class CompositePattern {

    public static void main(String[] args) {
        //树枝 1 节点
        Composite cs1 = new Composite();
        //树枝 2 节点
        Composite cs2 = new Composite();
        //树枝 2 节点
        Composite cs3 = new Composite();

        //new 4 个树叶节点
        Leaf leaf1 = new Leaf("1");
        Leaf leaf2 = new Leaf("2");
        Leaf leaf3 = new Leaf("3");
        Leaf leaf4 = new Leaf("4");
        Leaf leaf5 = new Leaf("5");

        //添加叶子
        cs1.add(leaf1);

        //添加树枝
        cs1.add(cs2);
        //添加叶子
        cs2.add(leaf2);
        cs2.add(leaf3);

        //添加树枝
        cs1.add(cs3);
        //添加叶子
        cs3.add(leaf4);

        cs3.add(leaf5);

        cs1.operation();
    }
}
