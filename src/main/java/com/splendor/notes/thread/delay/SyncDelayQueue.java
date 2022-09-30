package com.splendor.notes.thread.delay;

import com.splendor.notes.thread.reactor.TaskProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @Author splendor.s
 * @create 2022/1/28 17:07
 */
@Slf4j
@Component
public class SyncDelayQueue extends DelayQueue<HasResourceDto> {

    @Resource
    private TaskProcessor taskProcessor;

    @Override
    @PostConstruct
    public void init() {
        String threadName = "医生有号提醒数据同步重试";
        super.init(threadName);
        super.executorService.submit(this);
    }

    @Override
    public void run() {
        if(!super.isClose.get()){
            HasResourceDto hasReource = null;
            try {
                DelayedWrapper<HasResourceDto> delayedWrapper = super.queue.take();
                hasReource = delayedWrapper.get();
                taskProcessor.process(hasReource.getHasResourceInfo());
            } catch (Exception e) {
                log.error("监控数据同步失败，错误信息为：{}" , e.getMessage() );
            }
        }
    }

    @PreDestroy
    @Override
    public void shutdown() {
        super.shutdown();
    }
}
