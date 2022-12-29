package com.splendor.notes.spring.beans;

/**
 * @author splendor.s
 * @create 2022/11/6 下午3:40
 * @description
 */
public class BeansException extends RuntimeException {

    public BeansException(String msg){
        super(msg);
    }

    public BeansException(String msg, Throwable cause){
        super(msg, cause);
    }
}
