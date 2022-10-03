package com.splendor.notes.thread.syncTool.callback;

import com.splendor.notes.thread.syncTool.wrapper.WorkerWrapper;

import java.util.Map;

/**
 * @author splendor.s
 * @create 2022/10/3 22:47
 * @description  每个最小执行单元需要实现该接口
 */
@FunctionalInterface
public interface IWorker<T, V> {

    /**
     * 在这里做耗时操作，如rpc请求、IO等
     * @param object      object
     * @param allWrappers 任务包装
     * @return
     */
    V  action(T object, Map<String, WorkerWrapper> allWrappers);

    /**
     * 超时、异常时，返回的默认值
     * @return 默认值
     */
    default V defaultValue(){
        return null;
    }

}
