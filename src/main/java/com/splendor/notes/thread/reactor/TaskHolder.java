package com.splendor.notes.thread.reactor;

/**
 * @Author splendor.s
 * @create 2022/9/27 19:57
 * @Description 任务实体
 */
public class TaskHolder<ID , T> {

    /**
     * 任务ID
     */
    private final ID id;
    /**
     * 具体任务
     */
    private final T task;
    /**
     * 有效时间
     */
    private final long expiryTime;
    /**
     * 任务提交时间
     */
    private final long submitTimestamp;

    public TaskHolder(ID id, T task, long expiryTime, long submitTimestamp) {
        this.id = id;
        this.expiryTime = System.currentTimeMillis() + expiryTime;
        this.task = task;
        this.submitTimestamp = submitTimestamp;
    }

    public ID getId() {
        return id;
    }

    public T getTask() {
        return task;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public long getSubmitTimestamp() {
        return submitTimestamp;
    }

}
