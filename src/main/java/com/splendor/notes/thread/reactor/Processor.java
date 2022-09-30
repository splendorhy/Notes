package com.splendor.notes.thread.reactor;

/**
 * @Author splendor.s
 * @create 2022/9/27 18:00
 * Description 批处理进程
 */
public interface Processor<ID , T> {

    void process(ID id, T task, long expiryTime, long submitTimestamp);

    void shutdown();

}
