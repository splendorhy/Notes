package com.splendor.notes.design.patterns.combination.base;

/**
 * @author splendor.s
 * @create 2023/5/5 下午5:55
 * @description 抽象构建定义
 */
public abstract class Component {

    public void add(Component component) {
        throw new UnsupportedOperationException("禁止操作");
    }

    public void remove(Component component) {
        throw new UnsupportedOperationException("禁止操作");
    }

    public Component getChild(int index) {
        throw new UnsupportedOperationException("禁止操作");
    }

    /**
     * 抽象打印方法留给子类实现
     */
    public abstract void operation();

}
