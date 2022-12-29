package com.splendor.notes.design.patterns.bridge.basic;

/**
 * @author splendor.s
 * @create 2022/12/28 下午4:53
 * @description
 */
public class UprightAbstractPhone extends AbstractPhone {

    protected UprightAbstractPhone(AbstractBranch branch) {
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
