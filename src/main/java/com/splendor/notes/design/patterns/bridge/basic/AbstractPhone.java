package com.splendor.notes.design.patterns.bridge.basic;

/**
 * @author splendor.s
 * @create 2022/12/28 下午4:40
 * @description  // 桥接类——手机抽象类
 */
public abstract class AbstractPhone {

    protected AbstractBranch branch;

    protected AbstractPhone(AbstractBranch branch){
        this.branch = branch;
    }

    public abstract  void open();

    public abstract void call();

    public abstract void close() ;
}
