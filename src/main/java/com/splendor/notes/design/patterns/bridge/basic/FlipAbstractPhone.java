package com.splendor.notes.design.patterns.bridge.basic;

/**
 * @author splendor.s
 * @create 2022/12/28 下午4:44
 * @description // 桥接子类——翻盖式手机
 */
public class FlipAbstractPhone extends AbstractPhone {
    public FlipAbstractPhone(AbstractBranch branch) {
        super(branch);
        System.out.println("翻盖式手机");
    }

    @Override
    public void open() {
        branch.open();
    }

    @Override
    public void call() {
        branch.call();
    }

    @Override
    public void close() {
        branch.close();
    }
}
