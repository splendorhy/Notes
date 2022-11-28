package com.splendor.notes.design.patterns.prototype;

/**
 * @author splendor.s
 * @create 2022/11/28 下午3:59
 * @description
 */
public class Client {
    public static void main(String[] args) {
        ConcreteProtoType concreteProtoType = new ConcreteProtoType();
        for (int i = 0; i < 10; i++) {
            ConcreteProtoType cloneConcreteProtoType = (ConcreteProtoType) concreteProtoType.clone();
            cloneConcreteProtoType.proto();
        }
    }
}
