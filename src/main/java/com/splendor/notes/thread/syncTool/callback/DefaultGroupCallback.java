package com.splendor.notes.thread.syncTool.callback;

import com.splendor.notes.thread.syncTool.wrapper.WorkerWrapper;

import java.util.List;

/**
 * @author splendor.s
 * @create 2022/10/3 23:01
 * @description
 */
public class DefaultGroupCallback implements IGroupCallback {

    @Override
    public void success(List<WorkerWrapper> workerWrappers) {

    }

    @Override
    public void failure(List<WorkerWrapper> workerWrappers, Exception e) {

    }
}
