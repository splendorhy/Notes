package com.splendor.notes.design.patterns.prototype;

import lombok.Data;

/**
 * @author splendor.s
 * @create 2022/11/28 下午4:00
 * @description 复合形势下的原型模式，对员工增加对应的上司
 */
@Data
public class SupervisorPrototype implements Cloneable {
    private String name;

    /**
     * 功能描述：原型抽象类需要实现Cloneable接口,重写clone方法
     */
    @Override
    public SupervisorPrototype clone() {
        SupervisorPrototype supervisorPrototype = null;
        try {
            supervisorPrototype = (SupervisorPrototype) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return supervisorPrototype;
    }
}
