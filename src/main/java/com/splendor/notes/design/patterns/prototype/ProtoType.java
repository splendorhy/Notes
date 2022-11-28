package com.splendor.notes.design.patterns.prototype;

/**
 * @author splendor.s
 * @create 2022/11/28 下午4:00
 * @description 原型抽象类需要实现Cloneable接口
 */
public class ProtoType implements Cloneable {
    /*重写clone方法*/
    @Override
    public ProtoType clone() {
        ProtoType protoType = null;
        try {
            protoType = (ProtoType) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return protoType;
    }
}