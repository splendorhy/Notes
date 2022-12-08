package com.splendor.notes.thread.reactor;

import com.splendor.notes.infrastructure.util.constant.assembly.ConsumerAssembly;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author splendor.s
 * @create 2022/9/27 21:29
 */
@Slf4j
public class AcceptorExecutor<ID, T> {

    private AtomicBoolean isShutdown = new AtomicBoolean(false);
    private final BlockingDeque<TaskHolder<ID, T>> acceptorQueue = new LinkedBlockingDeque<>();
    private final BlockingDeque<TaskHolder<ID, T>> reprocessQueue = new LinkedBlockingDeque<>();

    private String name;

    private Integer flushMsgSize;

    private Long flushTime;

    private Integer maxBufferSize;

    private Long retrySleepTime;

    private Long serverCongestionTime;

    private final Thread acceptorThread;

    /**
     *  待办任务Map 和 ID任务存储队列
     */
    private final Map<ID, TaskHolder<ID, T>> pendingTasks = new HashMap<>();
    private final Deque<ID> processingOrder = new LinkedList<>();

    /**
     * 单条数据处理信号量对象 和 单条数据处理队列
     */
    private final Semaphore singleItemWorkRequests = new Semaphore(0);
    private final BlockingQueue<TaskHolder<ID, T>> singleItemWorkQueue = new LinkedBlockingQueue<>();

    /**
     * 批量数据处理信号量对象 和 批量数据处理队列
     */
    private final Semaphore batchWorkRequests = new Semaphore(0);
    private final BlockingQueue<List<TaskHolder<ID, T>>> batchWorkQueue = new LinkedBlockingQueue<>();

    private final TrafficShaper trafficShaper;

    private Integer index;

    public AcceptorExecutor(Integer index, String name, Integer flushMsgSize, Long flushTime, Integer maxBufferSize, Long retrySleepTime, Long serverCongestionTime) {
        this.index = index;
        this.name = name;
        this.flushMsgSize = flushMsgSize;
        this.flushTime = flushTime;
        this.maxBufferSize = maxBufferSize;
        this.retrySleepTime = retrySleepTime;
        this.serverCongestionTime = serverCongestionTime;
        this.trafficShaper = new TrafficShaper(serverCongestionTime, retrySleepTime);
        ThreadGroup threadGroup = new ThreadGroup(ConsumerAssembly.ACCEPTOR_THREAD_GROUP);
        this.acceptorThread = new Thread(threadGroup, new AcceptorExecutor.AcceptorRunner(), "TaskAcceptor-" + index);
        this.acceptorThread.setDaemon(true);
        this.acceptorThread.start();
    }

    /**
     * 正常处理程序
     * @param id
     * @param task
     * @param expiryTime
     * @param submitTimestamp
     */
    void process(ID id, T task, long expiryTime, long submitTimestamp){
        acceptorQueue.add(new TaskHolder<>(id, task, expiryTime, submitTimestamp));
    }

    /**
     * 批量处理失败的处理
     * @param holders
     * @param processingResult
     */
    void reprocess(List<TaskHolder<ID, T>> holders,TaskProcessor.ProcessingResult processingResult) {
        reprocessQueue.addAll(holders);
        trafficShaper.registerFailure(processingResult);
    }


    /**
     * 单条消费失败的处理
     * @param taskHolder
     * @param processingResult
     */
    void reprocess(TaskHolder<ID, T> taskHolder, TaskProcessor.ProcessingResult processingResult) {
        reprocessQueue.add(taskHolder);
        trafficShaper.registerFailure(processingResult);
    }

    /**
     *
     * @return
     */
    BlockingQueue<TaskHolder<ID, T>> requestWorkItem() {
        //释放信号量
        singleItemWorkRequests.release();
        return singleItemWorkQueue;
    }

    /**
     * 获取批量数据
     * @return
     */
    BlockingQueue<List<TaskHolder<ID, T>>> requestWorkItems() {
        //释放信号量
        batchWorkRequests.release();
        return batchWorkQueue;
    }

    /**
     * 获取处理失败的数据队列
     * @return
     */
    BlockingDeque<TaskHolder<ID, T>> requestRetryWorkItem(){
        return reprocessQueue;
    }

    void shutdown() {
        if (isShutdown.compareAndSet(false, true)) {
            acceptorThread.interrupt();
        }
    }

    class AcceptorRunner implements Runnable {
        @Override
        public void run() {
            long scheduleTime = 0;
            while (!isShutdown.get()) {
                try {
                    drainInputQueues();

                    int totalItems = processingOrder.size();

                    long now = System.currentTimeMillis();
                    if (scheduleTime < now) {
                        scheduleTime = now + trafficShaper.transmissionDelay();
                    }
                    if (scheduleTime <= now) {
                        assignBatchWork();
                        assignSingleItemWork();
                    }
                    if (totalItems == processingOrder.size()) {
                        Thread.sleep(10);
                    }
                } catch (Throwable e) {
                    log.warn("Discovery AcceptorThread error", e);
                }
            }
        }

        private void drainInputQueues(){

            drainReprocessQueue();

            drainAcceptorQueue();
        }

        private void drainReprocessQueue(){
            long now = System.currentTimeMillis();

            while (!isFull() && !reprocessQueue.isEmpty()){
                TaskHolder<ID, T> taskHolder = reprocessQueue.pollLast();
                ID id = taskHolder.getId();
                if (taskHolder.getExpiryTime() <= now) {
                    //TODO
                } else if (pendingTasks.containsKey(id)) {
                    //TODO
                } else {
                    pendingTasks.put(id, taskHolder);
                    processingOrder.addFirst(id);
                }
            }
            if (isFull()) {
                reprocessQueue.clear();
            }
        }

        private void drainAcceptorQueue(){
            while (!acceptorQueue.isEmpty()){
                appendDoctorTaskHolder(acceptorQueue.poll());
            }
        }

        private void appendDoctorTaskHolder(TaskHolder<ID, T> taskHolder){
            if(isFull()){
                pendingTasks.remove(processingOrder.poll());
            }
            TaskHolder<ID, T> previousTask =  pendingTasks.put(taskHolder.getId() , taskHolder);
            if (previousTask == null) {
                //记录放入队列失败的任务ID
                processingOrder.add(taskHolder.getId());
            }
        }
        private boolean isFull() {
            return pendingTasks.size() >= maxBufferSize;
        }
    }

    private void assignSingleItemWork() {
        if(!processingOrder.isEmpty()){
            if(singleItemWorkRequests.tryAcquire(1)){
               while (!processingOrder.isEmpty()) {
                   ID id =  processingOrder.poll();
                   TaskHolder<ID, T> holder = pendingTasks.remove(id);
                   //放入队列
                   singleItemWorkQueue.add(holder);
                   return;
               }
               //释放信号量
                singleItemWorkRequests.release();
            }
        }
    }

    void assignBatchWork(){
        if (hasEnoughTasksForNextBatch()){
            //只允许一个线程进入
           if(batchWorkRequests.tryAcquire(1)){
               //取两者最小值
               int len = Math.min(flushMsgSize, processingOrder.size());
               List<TaskHolder<ID, T>> holders = new ArrayList<>(len);
               //获取一批待处理的任务
               while (holders.size() < len && !processingOrder.isEmpty()) {
                   ID id = processingOrder.poll();
                   TaskHolder<ID, T> holder = pendingTasks.remove(id);
                   holders.add(holder);
               }
               if (holders.isEmpty()) {
                   //释放信号量
                   batchWorkRequests.release();
               } else {
                   //放入队列中
                   batchWorkQueue.add(holders);
               }
           }
        }
    }

    private boolean hasEnoughTasksForNextBatch() {
        if (processingOrder.isEmpty()) {
            return false;
        }
        if (pendingTasks.size() >= maxBufferSize) {
            return true;
        }
        TaskHolder<ID, T> nextHolder = pendingTasks.get(processingOrder.peek());
        long delay = System.currentTimeMillis() - nextHolder.getSubmitTimestamp();
        return delay >= flushTime;
    }
}
