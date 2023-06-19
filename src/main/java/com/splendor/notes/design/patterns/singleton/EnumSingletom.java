package com.splendor.notes.design.patterns.singleton;

/**
 * @author splendor.s
 * @create 2023/6/19 下午6:26
 * @description
 */
public class EnumSingletom {

    //私有化构造函数
    private EnumSingletom(){ }

    //定义一个静态枚举类
    static enum SingletonEnum{
        //创建一个枚举对象，该对象天生为单例
        INSTANCE;
        private EnumSingletom enumSingletom;
        //私有化枚举的构造函数
        private SingletonEnum(){
            enumSingletom = new EnumSingletom();
        }
        public EnumSingletom getInstnce(){
            return enumSingletom;
        }
    }

    //对外暴露一个获取EnumSingletom对象的静态方法
    public static EnumSingletom getInstance(){
        return EnumSingletom.SingletonEnum.INSTANCE.getInstnce();
    }

}

