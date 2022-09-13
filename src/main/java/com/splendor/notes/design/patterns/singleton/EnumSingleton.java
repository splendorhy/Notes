package com.splendor.notes.design.patterns.singleton;

/**
 * @author splendor.s
 * @create 2022/9/13 下午8:34
 *  描述：注册式单例模式/登记式单例模式，将每个实例都登记到一个地方，使用唯一的标识获取单例。
 *  注册单例模式有两种：枚举式单例模式+容器式单例模式
 *  此为枚举式单例模式---Effective Java推荐单例模式
 */
public enum EnumSingleton {

    /*枚举式单例模式*/
    INSTANCE;

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static EnumSingleton getInstance() {
        return INSTANCE;
    }


}
