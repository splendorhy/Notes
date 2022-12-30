package com.splendor.notes.infrastructure.status;

import com.splendor.notes.infrastructure.status.service.CompareTaskMapper;
import com.splendor.notes.infrastructure.status.service.CompareTaskPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author splendor.s
 * @create 2022/11/29 上午11:26
 * @description  任务处理模版
 * 基本任务处理的基类，包含通用逻辑：
 *
 * 将提交的处理交由线程池管理。
 * 同时定义一个心跳关联到外部的Processor，当processor运行结束时，结束心跳。每个ping关联一个单独的ScheduledExecutorService，结束ping时直接shutdown线程池。
 * 每个processor在开始前需要有一定逻辑更新task的状态，否则可能导致任务被重复提交。
 */


public abstract class AbstractProcessor implements Runnable {

    private final Ping ping;

    private final CompareTaskPo value;

    private final Semaphore semaphore = new Semaphore(0);

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractProcessor.class);

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(8,
            16, 5, TimeUnit.MINUTES, new LinkedBlockingQueue<>(), new ThreadFactory() {
        private AtomicInteger threadCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "thread-processor-" + threadCount.getAndIncrement());
        }
    });

    protected AbstractProcessor(CompareTaskPo value) {
        ping = new Ping(this);
        this.value = value;
    }


   /**
     * 心跳。
     * 关联到外部的Processor，当processor运行结束时，结束心跳。
     * 每个ping关联一个单独的ScheduledExecutorService，结束ping时直接shutdown线程池。
     */

    class Ping implements Runnable {

        private WeakReference<AbstractProcessor> weakReference;
        private ReferenceQueue<AbstractProcessor> referenceQueue = new ReferenceQueue<>();
        private ScheduledExecutorService scheduleAtFixedRate = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "task-ping"));
        private CompareTaskMapper compareTaskMapper;

        Ping(AbstractProcessor processor) {
            weakReference = new WeakReference<>(processor, referenceQueue);
            //compareTaskMapper = BeanFactoryUtil.getBean(CompareTaskMapper.class);
            compareTaskMapper = null;
        }

        void ping() {
            if (referenceQueue.poll() != null) {
                /*兜底：当其关联的processor被垃圾回收后，结束心跳*/
                LOGGER.warn("【任务处理心跳】compareTaskId：{}的心跳被动结束", value.getId());
                scheduleAtFixedRate.shutdown();
            } else {
                try {
                    int curTime = (int) (System.currentTimeMillis() / 1000);
                    compareTaskMapper.updateLastPingTime(value.getId(), curTime);
                    LOGGER.warn("【任务处理心跳】compareTaskId：{}心跳正常，当前时间:{} processor:{}", value.getId(), curTime, weakReference.get());
                } catch (Exception e) {
                    LOGGER.error("【任务处理心跳】compareTaskId：{}心跳时间更新异常，exception：", value.getId(), e);
                }
            }
        }
        @Override
        public void run() {
            ping();
        }

        void start() {
            LOGGER.warn("【任务处理心跳】compareTaskId：{}心跳正常开启", value.getId());
            scheduleAtFixedRate.scheduleWithFixedDelay(this, 2, 2, TimeUnit.SECONDS);
        }

        void close() {
            LOGGER.warn("【任务处理心跳】compareTaskId：{}心跳正常结束", value.getId());
            scheduleAtFixedRate.shutdown();
        }
    }

    protected abstract boolean actualProcess(CompareTaskPo value);

    protected abstract void end(CompareTaskPo value);

    private void done() {
        ping.close();
        semaphore.release(1);
    }

    public final void process() {
        THREAD_POOL_EXECUTOR.submit(this);
    }

    public final boolean allowRecycle() {
        return semaphore.tryAcquire();
    }

    @Override
    public final void run() {
        this.ping.start();
        try {
             /*实际状态下任务处理内容成功后进行状态流转*/
            if (actualProcess(value)) {
                end(value);
            }
        } finally {
            done();
        }
    }
}
