package com.splendor.notes.thread.syncTool.callback;

import com.splendor.notes.thread.syncTool.wrapper.WorkerWrapper;

import java.util.List;

/**
 * @author splendor.s
 * @create 2022/10/3 22:46
 * @description 如果是异步执行整组的话，可以用这个组回调。不推荐使用
 */
public interface IGroupCallback {

    /**
     * 成功后，可以从wrapper里去getWorkResult
     * @param workerWrappers
     */
    void success(List<WorkerWrapper> workerWrappers);

    /**
     * 失败了，也可以从wrapper里去getWorkResult
     * @param workerWrappers
     * @param e
     */
    void failure(List<WorkerWrapper> workerWrappers, Exception e);

}
