package com.splendor.notes.design.patterns.bridge.basic;

/**
 * @author splendor.s
 * @create 2022/12/28 下午4:51
 * @description // 桥接子类——滑盖式手机
 */
public class SlideAbstractPhone extends AbstractPhone {

    protected SlideAbstractPhone(AbstractBranch branch) {
        super(branch);
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
