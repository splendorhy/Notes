package com.splendor.notes.design.patterns.bridge.basic;

/**
 * @author splendor.s
 * @create 2022/12/28 下午4:38
 * @description 华为品牌
 */
public class HuaweiBranchImpl implements AbstractBranch {

    @Override
    public void open() {
        System.out.println("华为手机开机");
    }

    @Override
    public void call() {
        System.out.println("华为手机打电话");
    }

    @Override
    public void close() {
        System.out.println("华为手机关机");
    }
}
