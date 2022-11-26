package com.splendor.notes.design.ddd.infrastructure.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author splendor.s
 * @create 2022/11/25 下午5:53
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {

    Status status;
    String msg;
    Object data;

    public static Response ok(){
        return Response.builder().status(Status.SUCCESS).build();
    }

    public static Response ok(Object data){
        return Response.builder().status(Status.SUCCESS).data(data).build();
    }

    public static Response failed(String msg){
        return Response.builder().status(Status.FAILED).msg(msg).build();
    }

    public enum Status{
        SUCCESS, FAILED
    }
}
