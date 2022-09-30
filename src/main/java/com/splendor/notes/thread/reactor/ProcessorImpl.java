package com.splendor.notes.thread.reactor;


/**
 * @Author splendor.s
 * @create 2022/9/27 19:28
 */
public class ProcessorImpl implements Processor<String , HasResourceInfo> {

    private AcceptorExecutor acceptorExecutor;

    private TaskExecutors taskExecutor;


    public ProcessorImpl(AcceptorExecutor acceptorExecutor, TaskExecutors taskExecutor) {
        this.acceptorExecutor = acceptorExecutor;
        this.taskExecutor = taskExecutor;
    }

    @Override
    public void process(String id, HasResourceInfo task, long expiryTime, long submitTimestamp) {
        acceptorExecutor.process(id, task, expiryTime, submitTimestamp);
    }

    @Override
    public void shutdown() {
        acceptorExecutor.shutdown();
        taskExecutor.shutdown();
    }
}
