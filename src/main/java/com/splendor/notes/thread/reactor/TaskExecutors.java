package com.splendor.notes.thread.reactor;

import com.google.common.collect.Lists;
import com.splendor.notes.infrastructure.util.constant.assembly.ConsumerAssembly;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author splendor.s
 * @create 2022/9/27 19:28
 */
@Slf4j
public class TaskExecutors<ID,T> {

    private final AtomicBoolean isShutdown;
    private final List<Thread> workerThreads;

    TaskExecutors(WorkerRunnableFactory<ID, T> workerRunnableFactory, int workerCount, AtomicBoolean isShutdown){
        this.isShutdown = isShutdown;
        this.workerThreads = Lists.newArrayList();
        ThreadGroup threadGroup = new ThreadGroup(ConsumerAssembly.ACCEPTOR_THREAD_GROUP);
        for (int i = 0; i < workerCount; i++) {
            TaskExecutors.WorkerRunnable<ID, T> runnable = workerRunnableFactory.create(i);
            Thread workerThread = new Thread(threadGroup, runnable, runnable.getWorkerName());
            workerThreads.add(workerThread);
            workerThread.setDaemon(true);
            workerThread.start();
        }
    }

    interface WorkerRunnableFactory<ID, T> {
        WorkerRunnable<ID, T> create(int idx);
    }

    abstract static  class WorkerRunnable<ID, T> implements Runnable{

        final String workerName;

        final AtomicBoolean isShutdown;

        final TaskProcessor<T> processor;

        final AcceptorExecutor<ID,T> taskDispatcher;

        WorkerRunnable(String workerName,
                       AtomicBoolean isShutdown,
                       TaskProcessor<T> processor,
                       AcceptorExecutor<ID , T> taskDispatcher){
            this.isShutdown = isShutdown;
            this.workerName = workerName;
            this.processor = processor;
            this.taskDispatcher = taskDispatcher;
        }

        String getWorkerName() {
            return workerName;
        }
    }


    static <ID, T> TaskExecutors<ID, T> singleItemExecutors(final String name,
                                                            final Integer workerCount,
                                                            final TaskProcessor<T> processor,
                                                            final AcceptorExecutor<ID, T> acceptorExecutor) {
        final AtomicBoolean isShutdown = new AtomicBoolean();
        return new TaskExecutors<>(idx -> new SingleWorkerRunnable<>("TaskNonBatchingWorker-" + name + '-' + idx, isShutdown, processor, acceptorExecutor), workerCount, isShutdown);
    }

    static class SingleWorkerRunnable<ID, T> extends WorkerRunnable<ID, T> {

        SingleWorkerRunnable(String workerName,
                                 AtomicBoolean isShutdown,
                                 TaskProcessor<T> processor,
                                 AcceptorExecutor<ID, T> acceptorExecutor) {
            super(workerName, isShutdown, processor, acceptorExecutor);
        }

        @Override
        public void run() {
            try {
                while (!isShutdown.get()) {
                    BlockingQueue<TaskHolder<ID, T>> workQueue = taskDispatcher.requestWorkItem();

                    singleWorkerEncapsulation(workQueue);
                }
            } catch (InterruptedException e) {
                // Ignore
            } catch (Throwable e) {
                // Safe-guard, so we never exit this loop in an uncontrolled way.
                log.error("Discovery WorkerThread error", e);
            }
        }

        private void singleWorkerEncapsulation(BlockingQueue<TaskHolder<ID,T>> workQueue) throws InterruptedException {
            TaskHolder<ID, T> taskHolder;
            while ((taskHolder = workQueue.poll(1, TimeUnit.SECONDS)) == null) {
                if (isShutdown.get()) {
                    return;
                }
            }
            TaskProcessor.ProcessingResult result = processor.process(taskHolder.getTask());
            switch (result) {
                case Success:
                    break;
                case Congestion:
                case TransientError:
                    taskDispatcher.reprocess(taskHolder, result);
                    break;
                case PermanentError:
                    log.warn("Discarding a task of {} due to permanent error", workerName);
                    break;
                default:
                    return;
            }

        }
    }

    static <ID, T> TaskExecutors<ID, T> batchExecutors(final String name,
                                                                                                            final Integer workerCount,
                                                                                                            final TaskProcessor<T> processor,
                                                                                                            final AcceptorExecutor<ID, T> acceptorExecutor) {
        final AtomicBoolean isShutdown = new AtomicBoolean();
        return new TaskExecutors<>(idx -> new BatchWorkerRunnable<>("TaskBatchingWorker-" + name + '-' + idx, isShutdown, processor, acceptorExecutor), workerCount, isShutdown);
    }

    static class BatchWorkerRunnable<ID,T> extends WorkerRunnable<ID,T> {
        BatchWorkerRunnable(String workerName,
                            AtomicBoolean isShutdown,
                            TaskProcessor<T> processor,
                            AcceptorExecutor<ID, T> acceptorExecutor) {
            super(workerName, isShutdown, processor, acceptorExecutor);
        }

        @Override
        public void run() {
            try {
                while (!isShutdown.get()) {
                    List<TaskHolder<ID, T>> holders = getWork();

                    List<T> tasks = getTasksOf(holders);
                    TaskProcessor.ProcessingResult result = processor.process(tasks);
                    switch (result) {
                        case Success:
                            break;
                        case Congestion:
                        case TransientError:
                            taskDispatcher.reprocess(holders, result);
                            break;
                        case PermanentError:
                            log.warn("Discarding {} tasks of {} due to permanent error", holders.size(), workerName);
                            break;
                        default:
                            return;
                    }
                }
            } catch (InterruptedException e) {
                log.warn("Discovery WorkerThread error", e);
            }
        }

        private List<T> getTasksOf(List<TaskHolder<ID,T>> holders) {
            List<T> tasks = new ArrayList<>(holders.size());
            for(TaskHolder<ID, T> taskHolder : holders){
                tasks.add(taskHolder.getTask());
            }
            return tasks;

        }

        private List<TaskHolder<ID, T>> getWork() throws InterruptedException {
            BlockingQueue<List<TaskHolder<ID, T>>> workQueue = taskDispatcher.requestWorkItems();
            List<TaskHolder<ID, T>> result;
            do {
                result = workQueue.poll(1, TimeUnit.SECONDS);
            } while (!isShutdown.get() && result == null);
            return (result == null) ? new ArrayList<>() : result;
        }
    }

    void shutdown() {
        if (isShutdown.compareAndSet(false, true)) {
            for (Thread workerThread : workerThreads) {
                workerThread.interrupt();
            }
        }
    }
}
