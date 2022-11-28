package com.splendor.notes.design.patterns.decorator.basic;

/**
 * @author splendor.s
 * @create 2022/11/28 下午3:17
 * @description  装饰模式测试
 */
public class Test {
    public static void main(String[] args) {
        IDecorator decorator = new Decorator();
        IDecorator curtainDecorator = new CurtainDecorator(decorator);
        curtainDecorator.decorate();
    }
}
