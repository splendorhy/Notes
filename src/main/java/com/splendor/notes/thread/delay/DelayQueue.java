package com.splendor.notes.thread.delay;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author splendor.s
 * @Description 数据延迟队列
 **/
public abstract class DelayQueue<T> implements Runnable, LifeCycle {

    public AtomicBoolean isClose = new AtomicBoolean(false);
    public java.util.concurrent.DelayQueue<DelayedWrapper<T>> queue = new java.util.concurrent.DelayQueue<>();
    public ExecutorService executorService;


    public void init(String threadName){
        executorService = Executors.newSingleThreadExecutor(r -> {
            Thread thread = new Thread(r, threadName);
            thread.setDaemon(true);
            return thread;
        });

    }

    @Override
    public void shutdown() {
        if (isClose.compareAndSet(false,true)){
            executorService.shutdown();
        }
    }

    /**
     * 加入队列
     *
     * @param data
     */
    public void add(T data) {
        queue.add(new DelayedWrapper<>(data, 10, TimeUnit.SECONDS));
    }

    public void add(T data, int seconds) {
        queue.add(new DelayedWrapper<>(data, seconds, TimeUnit.SECONDS));
    }

    public void add(T data, int time, TimeUnit timeUnit) {
        queue.add(new DelayedWrapper<>(data, time, timeUnit));
    }

    class DelayedWrapper<T> implements Delayed {
        private long deadline;
        private T bean;


        public DelayedWrapper(T bean, long delayTime, TimeUnit delayTimeUnit) {
            this.deadline = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(delayTime, delayTimeUnit);
            this.bean = bean;
        }


        @Override
        public long getDelay(TimeUnit unit) {
            long delay = unit.convert(deadline - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
            return delay;
        }


        @Override
        public int compareTo(Delayed o) {
            long diff;
            if (o instanceof DelayedWrapper) {
                diff = this.deadline - ((DelayedWrapper) o).getDeadline();
            } else {
                diff = this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
            }
            return (diff == 0) ? 0 : ((diff < 0) ? -1 : 1);
        }

        public T get() {
            return bean;
        }

        /**
         * 获取 deadline
         *
         * @return deadline
         */
        public long getDeadline() {
            return deadline;
        }

        @Override
        public String toString() {
            return "DelayedWrapper{" +
                    "deadline=" + deadline +
                    ", bean=" + bean +
                    '}';
        }
    }

}
