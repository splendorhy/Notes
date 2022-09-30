package com.splendor.notes.thread.reactor;

import com.google.common.collect.Lists;

import java.util.List;


/**
 * @Author splendor.s
 * @create 2022/4/27 18:03
 * @Description 消费内存队列
 */
public class ConsumerQueue {

    private final List<Processor> queues = Lists.newArrayList();

    private ConsumerQueue(){}

    /**
     * 单例
     */
    private static class Singleton {
        private static final ConsumerQueue instance;
        static {
            instance = new ConsumerQueue();
        }
        public static ConsumerQueue getInstance() {
            return instance;
        }
    }

    /**
     * 获取单例
     * @return
     */
    public static ConsumerQueue getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 获取内存队列的数量
     *
     * @return
     */
    public int queueSize() {
        return queues.size();
    }

    public void addProcess(Processor queue) {
        this.queues.add(queue);
    }

    public void clearQueue() {
        this.queues.clear();
    }

    /**
     * 获取内存队列
     *
     * @param index
     * @return
     */
    public Processor getProcess(int index) {
        return queues.get(index);
    }
}
