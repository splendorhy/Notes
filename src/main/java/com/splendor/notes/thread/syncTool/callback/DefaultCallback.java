package com.splendor.notes.thread.syncTool.callback;

import com.splendor.notes.thread.syncTool.wrapper.WorkerWrapper;

import java.util.List;

/**
 * @Author splendor.s
 * @create 2022/10/3 22:41
 * 默认回调类，如果不设置的话，会默认给这个回调
 */
public class DefaultCallback implements IGroupCallback   {

    @Override
    public void success(List<WorkerWrapper> workerWrappers) {

    }

    @Override
    public void failure(List<WorkerWrapper> workerWrappers, Exception e) {

    }
}
