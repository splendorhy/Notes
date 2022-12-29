package com.splendor.notes.spring.beans;

/**
 * @author splendor.s
 * @create 2022/11/6 下午3:40
 * @description bean 属性信息
 */
public class PropertyValue {

    private String name;

    private Object value;

    public PropertyValue(String name, Object value){
        this.name = name;
        this.value = value;
    }

    public String getName(){
        return name;
    }

    public Object getValue(){
        return value;
    }

}
