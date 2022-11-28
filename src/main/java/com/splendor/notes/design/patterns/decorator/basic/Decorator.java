package com.splendor.notes.design.patterns.decorator.basic;

/**
 * @author splendor.s
 * @create 2022/11/28 下午3:17
 * @description
 */
public class Decorator implements IDecorator {
    /**
     * 基本实现方法
     */
    @Override
    public void decorate() {
        System.out.println("水电装修、天花板以及粉刷墙.");
    }
}
