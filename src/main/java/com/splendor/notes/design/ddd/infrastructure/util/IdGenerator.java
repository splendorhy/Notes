package com.splendor.notes.design.ddd.infrastructure.util;

import java.util.UUID;

/**
 * @author splendor.s
 * @create 2022/11/25 下午5:52
 * @description
 */
public class IdGenerator {

    public static String nextId(){
        return UUID.randomUUID().toString();
    }

}
