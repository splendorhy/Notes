package com.splendor.notes.thread.syncTool.callback;

import com.splendor.notes.thread.syncTool.worker.WorkResult;

/**
 * @author splendor.s
 * @create 2022/10/3 22:44
 * @description 每个执行单元执行完毕后，会回调该接口  需要监听执行结果的，实现该接口即可
 */
@FunctionalInterface
public interface ICallback<T ,V> {

    /**
     * 任务开始的监听
     */
    default void begin() { }

    /**
     * 耗时操作执行完毕后，就给value注入值
     * @param success
     * @param param
     * @param workResult
     */
    void result(boolean success, T param, WorkResult<V> workResult);

}
