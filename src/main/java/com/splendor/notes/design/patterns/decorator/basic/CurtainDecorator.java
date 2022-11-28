package com.splendor.notes.design.patterns.decorator.basic;

/**
 * @author splendor.s
 * @create 2022/11/28 下午3:17
 * @description 窗帘装饰类
 */
public class CurtainDecorator extends BaseDecorator {
    public CurtainDecorator(IDecorator decorator) {
        super(decorator);
    }

    /**
     * 窗帘具体装饰方法
     */
    @Override
    public void decorate() {
        System.out.println("窗帘装饰。。。");
        super.decorate();
    }
}
