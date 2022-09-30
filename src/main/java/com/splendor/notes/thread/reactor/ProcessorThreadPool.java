package com.splendor.notes.thread.reactor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author splendor.s
 * @create 2022/9/27 19:27
 * @Description 任务处理线程池初始化组件
 */
@Slf4j
@Component
public class ProcessorThreadPool {

    /**
     *缓存最大值
     */
    @Value("${log.flush.size:20}")
    private Integer flushMaxSize;

    /**
     * 缓存最小值
     */
    @Value("${log.flush.size:1}")
    private Integer flushMinSize;

    /**
     * 刷盘时间
     */
    @Value("${log.flush.time:500}")
    private Long flushTime;

    /**
     * 最大线程数
     */
    @Value("${log.max.maxWorkerCount:2}")
    private Integer maxWorkerCount;

    /**
     * 最小线程数
     */
    @Value("${log.min.minWorkerCount:1}")
    private Integer minWorkerCount;

    /**
     * 最大缓冲区
     */
    @Value("${log.max.maxBufferSize:10000}")
    private Integer maxBufferSize;

    @Value("${log.retry.sleep.time:100}")
    private Long retrySleepTime;

    @Value("${log.server.congestion.time:1000}")
    private Long serverCongestionTime;

    /**
     *  线程数
     */
    @Value("${log.max.processSize:2}")
    private Integer processSize;

    /**
     * 初始化的便捷方法
     */
    @PostConstruct
    public void init(){
        ConsumerQueue requestQueue = ConsumerQueue.getInstance();

        initProcessor(requestQueue , processSize);

    }

    private void initProcessor(ConsumerQueue requestQueue , Integer processSize){
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String name = address.getHostName();
        TaskProcessor taskProcessor = new TaskProcessorImpl();

        //开启线程池大小
        for (int i = 0; i < processSize; i++) {
            final AcceptorExecutor acceptorExecutor = new AcceptorExecutor( i, name,
                    flushMaxSize, flushTime, maxBufferSize, retrySleepTime, serverCongestionTime);
            final TaskExecutors taskExecutor = TaskExecutors.singleItemExecutors(name, maxWorkerCount, taskProcessor, acceptorExecutor);
            requestQueue.addProcess(new ProcessorImpl(acceptorExecutor, taskExecutor));
        }
    }

    @PreDestroy
    public void destroyed(){
        ConsumerQueue requestQueue = ConsumerQueue.getInstance();
        for (int i = 0,size = requestQueue.queueSize(); i < size; i++) {
            Processor process = requestQueue.getProcess(i);
            process.shutdown();
        }
    }
}
