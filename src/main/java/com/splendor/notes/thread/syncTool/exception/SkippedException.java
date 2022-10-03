package com.splendor.notes.thread.syncTool.exception;

/**
 * @Author splendor.s
 * @create 2022/10/3 22:39
 * 如果任务在执行之前，自己后面的任务已经执行完或正在被执行，则抛该exception
 */
public class SkippedException extends RuntimeException {

    public SkippedException() {
        super();
    }

    public SkippedException(String message) {
        super(message);
    }

}
